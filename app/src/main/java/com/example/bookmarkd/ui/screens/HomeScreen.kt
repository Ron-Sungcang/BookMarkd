package com.example.bookmarkd.ui.screens

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ArtTrack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookmarkd.R
import com.example.bookmarkd.model.Book
import com.example.bookmarkd.ui.screens.components.BookAppBar
import com.example.bookmarkd.ui.screens.components.BooksRow
import com.example.bookmarkd.ui.screens.components.DrawHeader
import com.example.bookmarkd.ui.screens.components.DrawerBody
import com.example.bookmarkd.ui.screens.components.MenuItem
import kotlinx.coroutines.launch

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(){
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val drawerScope = rememberCoroutineScope()


    val drawerItems = listOf(
        MenuItem(
            id = stringResource(id = R.string.home),
            title = stringResource(id = R.string.home),
            contentDesc = stringResource(id = R.string.home),
            icon = Icons.Filled.Home
        ),
        MenuItem(
            id = stringResource(id = R.string.search),
            title = stringResource(id = R.string.search),
            contentDesc = stringResource(id = R.string.search),
            icon = Icons.Filled.Search
        ),
        MenuItem(
            id = stringResource(id = R.string.profile),
            title = stringResource(id = R.string.profile),
            contentDesc = stringResource(id = R.string.profile),
            icon = Icons.Filled.Person
        ),
        MenuItem(
            id = stringResource(id = R.string.readlist),
            title = stringResource(id = R.string.readlist),
            contentDesc = stringResource(id = R.string.readlist),
            icon = Icons.Filled.AccessTime
        ),
        MenuItem(
            id = stringResource(id = R.string.lists),
            title = stringResource(id = R.string.lists),
            contentDesc = stringResource(id = R.string.lists),
            icon = Icons.Filled.ArtTrack
        ),
        MenuItem(
            id = stringResource(id = R.string.reviews),
            title = stringResource(id = R.string.reviews),
            contentDesc = stringResource(id = R.string.reviews),
            icon = Icons.Filled.Menu
        ),
        MenuItem(
            id = stringResource(id = R.string.settings),
            title = stringResource(id = R.string.settings),
            contentDesc = stringResource(id = R.string.settings),
            icon = Icons.Filled.Settings
        )
    )

    val bookListViewModel: BookListViewModel =
        viewModel(factory = BookListViewModel.Factory)
    bookListViewModel.getBooks("fiction")
    ModalNavigationDrawer(
        drawerContent = {
            Column {
                DrawHeader()
                DrawerBody(
                    items = drawerItems,
                    onItemClick = {}
                )
            }
        },
        drawerState = drawerState
    ) {

        Scaffold(
            topBar ={ BookAppBar(
                listOf(
                    stringResource(id = R.string.books),
                    stringResource(id = R.string.favourites),
                    stringResource(id = R.string.reviews),
                    stringResource(id = R.string.lists)
                ),
                canNavigateBack = false,
                navigateUp = { },
                expandMenu = {
                    drawerScope.launch {
                        drawerState.open()
                    }
                },
                onSearch = {

                },
                bookListUiState = bookListViewModel.bookListUiState,
            )
            }
        ){innerpadding ->
            LoadingScreen(Modifier.padding(innerpadding))
        }
        
    }
}


@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun HomeDisplay(
    bookListUiState: BookListUiState,
    currentScreen: String,
    modifier: Modifier = Modifier,
){
    when(bookListUiState) {
        is BookListUiState.Success -> when (currentScreen) {
                stringResource(id = R.string.books) -> BookScreen(
                    bookFavouriteList = emptyList(),
                    bookFictionList = bookListUiState.fiction,
                    bookNonFictionList = bookListUiState.nonFiction
                )
                stringResource(id = R.string.favourites) -> FavouriteScreen()
                stringResource(id = R.string.reviews) -> ReviewScreen()
                else -> ListScreen()
            }
        is BookListUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is BookListUiState.Error ->ErrorScreen(modifier = modifier.fillMaxSize(), retryAction = {})
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier){
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(id = R.drawable.loading_img),
        contentDescription = stringResource(id = R.string.loading)
    )
}

@Composable
fun ErrorScreen(retryAction: () -> Unit,modifier: Modifier = Modifier){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(id = R.string.retry))
        }
    }
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun BookScreen(
    bookFavouriteList: List<Book>,
    bookFictionList: List<Book>,
    bookNonFictionList: List<Book>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
){
    LazyColumn(modifier = modifier.fillMaxSize(),
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ){
        item {
            Text(
              text = stringResource(id = R.string.favourites),
              style = MaterialTheme.typography.titleMedium
            )
            BooksRow(bookList = bookFavouriteList)
            Spacer(modifier = Modifier.size(30.dp))
            Text(
               text = stringResource(id = R.string.fiction),
               style = MaterialTheme.typography.titleMedium
               )
            BooksRow(bookList = bookFictionList)
            Spacer(modifier = Modifier.size(30.dp))
            Text(
                text = stringResource(id = R.string.nonfiction),
                style = MaterialTheme.typography.titleMedium
            )
            BooksRow(bookList = bookNonFictionList)
        }
    }
}



@Composable
fun FavouriteScreen(modifier: Modifier = Modifier){
    Box(modifier = modifier.fillMaxSize()){
        Text(text = stringResource(id = R.string.favourites))
    }

}

@Composable
fun ReviewScreen(modifier: Modifier = Modifier){
    Box(modifier = modifier.fillMaxSize()){
        Text(text = stringResource(id = R.string.reviews))
    }

}

@Composable
fun ListScreen(modifier: Modifier = Modifier){
    Box(modifier = modifier.fillMaxSize()){
        Text(text = stringResource(id = R.string.lists))
    }

}