package com.example.bookmarkd.ui.screens.search_screen

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.bookmarkd.ui.screens.BookListViewModel
import com.google.android.material.search.SearchBar

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun SearchScreen(
    viewModel: BookListViewModel,
    modifier: Modifier = Modifier
){
    val uiState = viewModel.bookListUiState
    val uiStateSearch = viewModel.uiStateSearch.collectAsState().value
    val history =
        remember { mutableStateListOf<String>() }
}

/**
 * Takes a list of strings and displays it in a lazy column to be used for searcgh */
@Composable
fun SearchHistory(
    history: List<String>,
    onItemClick:(String) -> Unit,
    modifier: Modifier = Modifier
){
    LazyColumn(
        modifier = modifier
        .imePadding()){
        items(history){
            Text(
                text = it,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.clickable { onItemClick }
            )
        }
    }

}