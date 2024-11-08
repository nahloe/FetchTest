package com.example.fetchtest.data

import com.example.fetchtest.network.FetchApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

/**
 * DI Container
 */
interface FetchAppContainer {
    val fetchItemRepository: FetchItemRepository
}

/**
 * DI Container implementation for Application
 */
class DefaultAppContainer : FetchAppContainer {
    private val baseUrl = "https://fetch-hiring.s3.amazonaws.com/"

    private val retrofit: Retrofit = Retrofit.Builder()
        //using a kotlinx.serialization converter library:
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    /**
     *  lazy initialize API service
     */
    private val retrofitService: FetchApiService by lazy {
        retrofit.create(FetchApiService::class.java)
    }

    /**
     *  lazy initialize DI
     */
    override val fetchItemRepository: FetchItemRepository by lazy {
        NetworkFetchItemRepository(retrofitService)
    }
}