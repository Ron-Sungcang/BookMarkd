package com.example.bookmarkd.ui.screens.BookDetailsScreen

import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bookmarkd.R
import com.example.bookmarkd.model.Book
import com.example.bookmarkd.ui.screens.BookListViewModel
import com.example.bookmarkd.ui.screens.BookUiState
import com.example.bookmarkd.ui.screens.BookViewModel
import com.example.bookmarkd.ui.screens.ErrorScreen
import com.example.bookmarkd.ui.screens.LoadingScreen
import com.example.bookmarkd.ui.screens.components.FavouriteButton

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun BookDetailScreen(
    bookViewmodel: BookViewModel,
    bookListViewModel: BookListViewModel,
    modifier: Modifier = Modifier,
    retryAction: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(0.dp)

){
    when(val bookUiState = bookViewmodel.bookUiState){
        is BookUiState.Loading -> {
            LoadingScreen()
        }
        is BookUiState.Error -> {
            ErrorScreen(retryAction = retryAction)
        }
        is BookUiState.Success ->{
            BookDetail(bookListViewModel ,bookUiState.book, contentPadding)
        }
    }
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun BookDetail(
    bookListViewmodel: BookListViewModel,
    selectedBook: Book,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
){
    val scrollState = rememberScrollState()
    val layoutDirection = LocalLayoutDirection.current
    var favourite by remember{ mutableStateOf(false) }

    favourite = bookListViewmodel.isFavourite(selectedBook)

    Box(
        modifier = modifier
            .verticalScroll(state = scrollState)
            .padding(top = contentPadding.calculateTopPadding())
    ){
        Column(
            modifier = Modifier
                .padding(
                    bottom = contentPadding.calculateTopPadding(),
                    start = contentPadding.calculateStartPadding(layoutDirection),
                    end = contentPadding.calculateEndPadding(layoutDirection)
                )
        ) {
            Box {
                Box {
                    AsyncImage(
                        model = ImageRequest.Builder(context = LocalContext.current)
                            .data(
                                selectedBook.volumeInfo.imageLinks?.thumbnail?.replace(
                                    "http:",
                                    "https:"
                                )
                            )
                            .build(),
                        contentDescription = stringResource(id = R.string.book_photo),
                        error = painterResource(id = R.drawable.ic_broken_image),
                        placeholder = painterResource(id = R.drawable.loading_img),
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
                    Column(
                        Modifier
                            .align(Alignment.BottomStart)
                            .fillMaxWidth()
                            .background(
                                Brush.verticalGradient(
                                    listOf(Color.Transparent, MaterialTheme.colorScheme.scrim),
                                    0f,
                                    400f
                                )
                            )
                    ) {
                        Text(
                            text = selectedBook.volumeInfo.title,
                            style = MaterialTheme.typography.displaySmall,
                            color = MaterialTheme.colorScheme.inverseOnSurface,
                            modifier = Modifier
                                .padding(horizontal = dimensionResource(R.dimen.padding_small))
                        )
                        Text(
                            text = "Written by: " + selectedBook.volumeInfo.authors,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.inverseOnSurface
                        )
                        if (selectedBook.saleInfo.buyLink != null) {
                            Text(
                                text = "Buy now: " + selectedBook.saleInfo.buyLink,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        FavouriteButton(
                            favourite = favourite,
                            onFavouriteClick = {
                                if (favourite) {
                                    bookListViewmodel.removeFavouriteBook(selectedBook)
                                } else {
                                    bookListViewmodel.addFavouriteBook(selectedBook)
                                }
                                favourite = !favourite
                            }
                        )
                        Log.d(TAG, bookListViewmodel.favouriteBooks.size.toString())
                }
            }
            if (selectedBook.volumeInfo.description != null) {
                Text(
                    text = selectedBook.volumeInfo.description,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(
                        vertical = dimensionResource(R.dimen.padding_detail_content_vertical),
                        horizontal = dimensionResource(R.dimen.padding_detail_content_horizontal))
                )
            }
        }
    }

}