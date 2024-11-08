package com.example.fetchtest.dummy

import com.example.fetchtest.data.FetchItemRepository
import com.example.fetchtest.model.FetchItem

class DummyRepository:FetchItemRepository {
    override suspend fun getItems(): List<FetchItem> {
        return DummyData.itemList
    }
}