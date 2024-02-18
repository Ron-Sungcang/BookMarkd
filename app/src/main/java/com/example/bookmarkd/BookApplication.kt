package com.example.bookmarkd

import android.app.Application
import com.example.bookmarkd.data.AppContainer
import com.example.bookmarkd.data.DefaultAppContainer

class BookApplication: Application(){
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}