package com.example.bookmarkd.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.stringResource
import com.example.bookmarkd.R
import com.example.bookmarkd.ui.screens.components.BookAppBar
import com.example.bookmarkd.ui.screens.components.TabItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(){
    Scaffold(
        topBar ={ BookAppBar(
            listOf(
                TabItem(title = stringResource(id = R.string.books)),
                TabItem(title = stringResource(id = R.string.favourites)),
                TabItem(title = stringResource(id = R.string.reviews)),
                TabItem(title= stringResource(id = R.string.lists))
            ),
            canNavigateBack = false,
            navigateUp = { },
            expandMenu = { },
            onSearch = {  }
        )
        }
    ){innerpadding ->
        HomeDisplay(currentScreen = stringResource(id = R.string.books),
             Modifier.padding(innerpadding))
    }
}

@Composable
fun HomeDisplay(
    currentScreen: String,
    modifier: Modifier = Modifier,
){
    when(currentScreen){
        stringResource(id = R.string.books) -> BookScreen()
        stringResource(id = R.string.favourites) -> FavouriteScreen()
        stringResource(id = R.string.reviews) -> ReviewScreen()
        else -> ListScreen()
    }
}

@Composable
fun BookScreen(modifier: Modifier = Modifier){
    Box(modifier = modifier.fillMaxSize()){
        Text(text = stringResource(id = R.string.books))
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