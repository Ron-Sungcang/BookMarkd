package com.example.bookmarkd.data

import com.example.bookmarkd.network.BookApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

interface AppContainer{
    val bookApiService: BookApiService
    val bookRepository: BookRepository
}

class DefaultAppContainer: AppContainer{
    private val BASE_URL =
        "https://www.googleapis.com/books/v1/"



    override val bookApiService: BookApiService by lazy{
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create()
    }

    override val bookRepository: BookRepository by lazy {
        DefaultBookRepository(bookApiService)
    }

}

