package com.example.bookmarkd.data

import android.accounts.NetworkErrorException
import com.example.bookmarkd.model.Book
import com.example.bookmarkd.network.BookApiService


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
class DefaultBookRepository(
    private  val bookApiService: BookApiService
): BookRepository {
    override suspend fun getBook(id: String): Book? {
        return try {
            val res = bookApiService.getBook(id)
            if (res.isSuccessful) {
                res.body()
            } else {
                throw NetworkErrorException(res.code().toString())
            }
        } catch (e: Exception){
            e.printStackTrace()
            null
        }
    }

    override suspend fun getBooks(bookname: String): List<Book>? {
        return try {
            val res = bookApiService.getBooks(bookname)
            if(res.isSuccessful){
                res.body()?.items ?: emptyList()
            }else{
                throw NetworkErrorException(res.code().toString())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}