package com.example.bookmarkd.data

import com.example.bookmarkd.model.Book

/**
 * Repository retrieves book data from underlying data source.
 */
interface BookRepository{
    suspend fun getBooks(bookname: String): List<Book>?
    suspend fun getBook(id: String): Book?
}

/**
 * Network Implementation of repository that retrieves book data from underlying data source.
 */
class DefaultBookRepository(){

}