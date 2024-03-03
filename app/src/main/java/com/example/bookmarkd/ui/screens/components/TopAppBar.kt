package com.example.bookmarkd.ui.screens.components

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArtTrack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bookmarkd.R
import com.example.bookmarkd.model.Book
import com.example.bookmarkd.ui.screens.BookListUiState
import com.example.bookmarkd.ui.screens.BookScreen
import com.example.bookmarkd.ui.screens.HomeDisplay
import com.example.bookmarkd.ui.screens.favourite_screen.FavouriteUiState
import com.example.bookmarkd.ui.screens.search_screen.SearchUiState
import com.example.bookmarkd.ui.theme.BookMarkdTheme
import kotlinx.coroutines.flow.MutableStateFlow


//data class for menu items in the drawer body
data class MenuItem(
    val id: String,
    val title:String,
    val contentDesc: String,
    val icon: ImageVector
)

/**
 * This is the top app bar that deals with menu search and navigations
 * Uses tabrows to navigate between homescreen pages
 */
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun BookAppBar(
    currentScreen: BookScreen,
    tabItems: List<String>,
    bookListUiState: BookListUiState,
    bookFavouriteUiState: MutableStateFlow<FavouriteUiState>,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    expandMenu:() -> Unit,
    onSearch: () -> Unit,
    onBookClick: (Book) -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
    ) {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(id = currentScreen.title)) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                modifier = modifier,
                navigationIcon = {
                    if(canNavigateBack && currentScreen.title != R.string.home) {
                        IconButton(onClick = navigateUp) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = stringResource(R.string.back_button)
                            )
                        }
                    }else{
                    IconButton(onClick = expandMenu) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = stringResource(id = R.string.bars)
                            )
                        }
                    }
                },
                actions = {
                    IconButton(onClick = onSearch) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = stringResource(id = R.string.search)
                        )
                    }
                }
            )
        if(currentScreen.title == R.string.app_name|| currentScreen.title == R.string.home) {
            var selectedIndex by remember {
                mutableStateOf(0)
            }

            val pagerState = rememberPagerState {
                tabItems.size
            }
            LaunchedEffect(selectedIndex){
                pagerState.animateScrollToPage(selectedIndex)
            }
            LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress){
                if(!pagerState.isScrollInProgress){
                    selectedIndex = pagerState.currentPage
                }
            }
            TabRow(selectedTabIndex = selectedIndex) {
                tabItems.forEachIndexed { index, item ->
                    Tab(
                        selected = index == selectedIndex,
                        onClick = {
                            selectedIndex = index
                        },
                        text = {
                            Text(
                                item,
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    )
                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) { index ->
                HomeDisplay(currentScreen = tabItems[index], bookFavouriteUiState = bookFavouriteUiState, bookListUiState = bookListUiState, onBookClick = onBookClick)
            }
        }
    }
}

@Composable
fun DrawHeader(modifier: Modifier = Modifier){
    Box(
        modifier = modifier
            .width(280.dp)
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ){
        Row {
            Icon(imageVector = Icons.Filled.AccountCircle,
                contentDescription = null,
                Modifier.size(40.dp)
            )
            Text(
                text = "Account Profile",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}

@Composable
fun DrawerBody(
    items: List<MenuItem>,
    modifier: Modifier = Modifier,
    onItemClick: (MenuItem) -> Unit
){
    LazyColumn(
        modifier
            .width(280.dp)
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.background)){
        items(items){item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onItemClick(item)
                    }
                    .padding(dimensionResource(id = R.dimen.padding_medium))
            ) {
                Icon(imageVector = item.icon, contentDescription = item.contentDesc)
                Text(
                    text= item.title,
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun BookAppBarPreview(){
    BookMarkdTheme {

    }
}

@Preview(showBackground = true)
@Composable
fun DrawHeaderPreview(){
    BookMarkdTheme {
        DrawHeader()
    }
}

@Preview(showBackground = true)
@Composable
fun DrawBodyPreview(){
    BookMarkdTheme {
        DrawerBody(
            items = listOf(
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

            ),
            onItemClick = {}
        )
    }
}