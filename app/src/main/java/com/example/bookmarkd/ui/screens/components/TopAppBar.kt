package com.example.bookmarkd.ui.screens.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.bookmarkd.R
import com.example.bookmarkd.ui.screens.HomeDisplay
import com.example.bookmarkd.ui.theme.BookMarkdTheme

data class TabItem(
    val title: String
)

/**
 * This is the top app bar that deals with menu search and navigations
 * Uses tabrows to navigate between homescreen pages
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun BookAppBar(
    tabItems: List<TabItem>,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    expandMenu:() -> Unit,
    onSearch: () -> Unit,
    modifier: Modifier = Modifier
){
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
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        CenterAlignedTopAppBar(
            title = { Text(stringResource(id = R.string.app_name)) },
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            modifier = modifier,
            navigationIcon = {
                IconButton(onClick = expandMenu) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = stringResource(id = R.string.bars)
                    )
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
        TabRow(selectedTabIndex = selectedIndex) {
            tabItems.forEachIndexed { index, item ->
                Tab(
                    selected = index == selectedIndex,
                    onClick = {
                        selectedIndex = index
                    },
                    text = { Text(item.title) }
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {index ->
            HomeDisplay(currentScreen = tabItems[index].title)
       }

    }
}



@Preview
@Composable
fun BookAppBarPreview(){
    BookMarkdTheme {
        BookAppBar(
            listOf(
                TabItem(title = stringResource(id = R.string.books)),
                TabItem(title = stringResource(id = R.string.favourites)),
                TabItem(title = stringResource(id = R.string.reviews)),
                TabItem(title= stringResource(id = R.string.lists))
            ),
            canNavigateBack = false,
            navigateUp = { },
            expandMenu = { },
            onSearch = {  })
    }
}