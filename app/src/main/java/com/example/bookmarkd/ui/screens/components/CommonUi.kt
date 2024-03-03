package com.example.bookmarkd.ui.screens.components

import android.telecom.Call.Details
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bookmarkd.R
import com.example.bookmarkd.model.Book

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookPhotoCard(
    book: Book,
    modifier: Modifier = Modifier,
    onDetailsClick: (Book) -> Unit
){
    Card(
        onClick = {onDetailsClick(book)},
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
    ){
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(book.volumeInfo.imageLinks?.thumbnail?.replace("http:", "https:"))
                .build(),
            contentDescription = stringResource(id = R.string.book_photo),
            error = painterResource(id = R.drawable.ic_broken_image),
            placeholder = painterResource(id = R.drawable.loading_img),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        )
    }
}

/**
 * This is the card item that will be displayed on search
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookSearchCard(
    book: Book,
    modifier: Modifier = Modifier,
    onDetailsClick: (Book) -> Unit
){
    Card(
        onClick = {onDetailsClick(book)},
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .sizeIn(minHeight = 72.dp)) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(book.volumeInfo.imageLinks?.thumbnail?.replace("http:", "https:"))
                    .build(),
                contentDescription = stringResource(id = R.string.book_photo),
                error = painterResource(id = R.drawable.ic_broken_image),
                placeholder = painterResource(id = R.drawable.loading_img),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(180.dp)
            )
            Spacer(Modifier.width(dimensionResource(id = R.dimen.padding_medium)))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = book.volumeInfo.title,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "written by" + book.volumeInfo.authors,
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }
}

@Composable
fun BookGridScreen(
    bookList: List<Book>,
    modifier: Modifier = Modifier,
    onBookClick: (Book) -> Unit
){
    LazyVerticalGrid(
        columns = GridCells.Adaptive(120.dp),
        modifier =  modifier.padding(horizontal = 4.dp)
        ){
        items(items = bookList, key = {book -> book.id}){
            book -> BookPhotoCard(
            book = book,
            onDetailsClick = onBookClick,
            modifier = modifier
                .padding(4.dp)
                .fillMaxWidth()
            )
        }
    }
}

@Composable
fun BooksRow(
    bookList: List<Book>,
    modifier: Modifier = Modifier,
    onBookClick: (Book) -> Unit
){
    LazyRow(modifier = modifier){
        items(bookList){
            BookPhotoCard(book = it, onDetailsClick =onBookClick, modifier = Modifier.padding(10.dp))
        }
    }
}

@Composable
fun BookSearchList(
    bookList: List<Book>,
    modifier: Modifier = Modifier,
    onBookClick: (Book) -> Unit
){
    LazyColumn(modifier = modifier){
        items(bookList){item ->
            BookSearchCard(book = item, onDetailsClick = onBookClick)
        }
    }
}

@Composable
fun FavouriteButton(
    favourite: Boolean,
    onFavouriteClick: () -> Unit,
    modifier: Modifier = Modifier
){
    IconButton(
        onClick = onFavouriteClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = if (favourite) Icons.Filled.Favorite else Icons.Outlined.Favorite,
            tint = if (favourite) Color.Red else Color.LightGray,
            contentDescription = null
        )
    }
}