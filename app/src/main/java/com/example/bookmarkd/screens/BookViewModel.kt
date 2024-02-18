package com.example.bookmarkd.screens

import com.example.bookmarkd.model.Book

sealed interface BookListUiState{
    data class Success(val books: List<Book>): BookListUiState
    object Error: BookListUiState
    object Loading: BookListUiState
}

sealed interface BookUiState{
    data class Success(val book: Book): BookUiState
    object Error: BookUiState
    object Loading: BookUiState
}

