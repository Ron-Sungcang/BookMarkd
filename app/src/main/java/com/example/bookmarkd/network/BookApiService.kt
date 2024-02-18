package com.example.bookmarkd.network

import com.example.bookmarkd.model.Book
import com.example.bookmarkd.model.BooksQueryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BookApiService {
    @GET("volumes")
    suspend fun getBooks(@Query("bookname")bookname: String): Response<BooksQueryResponse>

    @GET("volumes/{id}")
    suspend fun getBook(@Path("id")id: String):Response<Book>
}
