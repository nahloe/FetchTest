package com.example.fetchtest

import com.example.fetchtest.dummy.DummyData
import com.example.fetchtest.dummy.DummyRepository
import com.example.fetchtest.rules.TestDispatcherRule
import com.example.fetchtest.ui.screens.HomeUiState
import com.example.fetchtest.ui.screens.HomeViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {
    @get: Rule
    val testDispatcher = TestDispatcherRule()

    /*
     *  Verify that the viewModel holds the correct items
     */
    @Test
    fun homeViewModel_getItems_verifyUiState() = runTest {
        val viewModel = HomeViewModel(fetchItemRepository = DummyRepository())
        assertEquals(
            viewModel.uiState,
            HomeUiState.Success(DummyData.itemList)
        )
    }
}