package com.example.bookmarkd.model

/**
 * this is for searches
 */
data class BooksQueryResponse (
    val kind: String,
    val totalItems: Int,
    val items: List<Book>
)