package com.example.fetchtest.dummy

import com.example.fetchtest.model.FetchItem
import com.example.fetchtest.network.FetchApiService

class DummyApiService : FetchApiService {
    override suspend fun getItems(): List<FetchItem> {
        return DummyData.itemList
    }
}