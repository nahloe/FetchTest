package com.example.fetchtest.network

import com.example.fetchtest.model.FetchItem
import retrofit2.http.GET

interface FetchApiService {

    /**
     * Download Json to List
     */
    @GET("hiring.json")
    suspend fun getItems(): List<FetchItem>
}