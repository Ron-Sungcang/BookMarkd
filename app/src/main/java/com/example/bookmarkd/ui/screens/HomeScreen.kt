package com.example.bookmarkd.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ArtTrack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.bookmarkd.R
import com.example.bookmarkd.ui.screens.components.BookAppBar
import com.example.bookmarkd.ui.screens.components.DrawHeader
import com.example.bookmarkd.ui.screens.components.DrawerBody
import com.example.bookmarkd.ui.screens.components.MenuItem
import kotlinx.coroutines.launch

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

                }
            )
            }
        ){innerpadding ->
            HomeDisplay(currentScreen = stringResource(id = R.string.books),
                Modifier.padding(innerpadding))
        }
        
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