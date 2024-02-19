package com.example.bookmarkd.ui.screens.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.bookmarkd.R
import com.example.bookmarkd.ui.theme.BookMarkdTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookAppBar(
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    expandMenu:() -> Unit,
    onSearch: () -> Unit,
    modifier: Modifier = Modifier
){
    Column {
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

    }
}

@Preview
@Composable
fun BookAppBarPreview(){
    BookMarkdTheme {
        BookAppBar(
            canNavigateBack = false,
            navigateUp = { /*TODO*/ },
            expandMenu = { /*TODO*/ },
            onSearch = { /*TODO*/ })
    }
}