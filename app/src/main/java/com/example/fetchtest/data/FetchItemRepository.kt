package com.example.fetchtest.data

import com.example.fetchtest.model.FetchItem
import com.example.fetchtest.network.FetchApiService

/**
 * Repository interface for Fetch items
 */
interface FetchItemRepository {
    suspend fun getItems(): List<FetchItem>
}

/**
 * Repository implementation for downloading Fetch items
 * @param fetchApiService API network service for Fetch items
 */
class NetworkFetchItemRepository(
    private val fetchApiService: FetchApiService
) : FetchItemRepository {
    override suspend fun getItems(): List<FetchItem> = fetchApiService.getItems()
}