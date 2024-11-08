package com.example.fetchtest

import android.app.Application
import com.example.fetchtest.data.DefaultAppContainer
import com.example.fetchtest.data.FetchAppContainer

class FetchApplication: Application() {
    lateinit var container: FetchAppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}