package com.example.bookmarkd.model

import kotlinx.serialization.Serializable

/**
 * this is for searches
 */
@Serializable
data class BooksQueryResponse (
    val kind: String,
    val totalItems: Int,
    val items: List<Book>?
)