package com.example.fetchtest

import com.example.fetchtest.data.NetworkFetchItemRepository
import com.example.fetchtest.dummy.DummyApiService
import com.example.fetchtest.dummy.DummyData
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.Assert.assertEquals

class FetchItemRepositoryTest {

    /**
     * Verify that the "downloaded" items match the list
     */
    @Test
    fun fetchItemRepository_getItems_verifyItems() = runTest {
        val repo = NetworkFetchItemRepository(fetchApiService = DummyApiService())
        assertEquals(
            DummyData.itemList,
            repo.getItems()
        )
    }
}