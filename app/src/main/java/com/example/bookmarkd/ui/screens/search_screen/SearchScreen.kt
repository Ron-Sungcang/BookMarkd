package com.example.bookmarkd.ui.screens.search_screen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import android.view.KeyEvent
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.bookmarkd.R
import com.example.bookmarkd.ui.screens.BookListUiState
import com.example.bookmarkd.ui.screens.BookListViewModel
import com.example.bookmarkd.ui.screens.ErrorScreen
import com.example.bookmarkd.ui.screens.LoadingScreen
import com.example.bookmarkd.ui.screens.components.BookSearchList

@OptIn(ExperimentalMaterial3Api::class)
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun SearchScreen(
    viewModel: BookListViewModel,
    modifier: Modifier = Modifier
){
    Log.d("search","search screen called")
    val focusManager = LocalFocusManager.current
    val uiState = viewModel.bookListUiState
    val uiStateSearch = viewModel.uiStateSearch.collectAsState().value
    val history =
        remember { mutableStateListOf<String>() }
    Column(modifier.fillMaxSize()) {
        OutlinedTextField(
            value = uiStateSearch.query,
            onValueChange = {viewModel.upDateSearch(it)},
            singleLine = true,
            placeholder = {
                Text(text = stringResource(id = R.string.search))
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    history.add(uiStateSearch.query)
                    viewModel.getBooks(uiStateSearch.query)
                    viewModel.updateSearchStarted(true)
                    focusManager.clearFocus()
                }
            ),
            modifier = Modifier
                .onKeyEvent { e ->
                    if (e.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_ENTER) {
                        viewModel.getBooks(uiStateSearch.query)
                        viewModel.updateSearchStarted(true)
                        focusManager.clearFocus()
                    }
                    false
                }
                .fillMaxWidth()

        )
        if(uiStateSearch.searchStarted){
            when(uiState){
                is BookListUiState.Loading -> LoadingScreen(modifier)
                is BookListUiState.Success -> BookSearchList(
                    bookList = uiState.books
                )
                is BookListUiState.Error -> ErrorScreen(retryAction = {}, modifier)
                else -> {}
            }
        } else{
            SearchHistory(history = history, onItemClick = {})
        }
    }
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
                    .fillMaxWidth()
            )
        }
    }

}