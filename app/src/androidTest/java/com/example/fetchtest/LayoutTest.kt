package com.example.fetchtest

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import com.example.fetchtest.model.FetchItem
import com.example.fetchtest.ui.screens.FetchItemListScreen
import org.junit.Rule
import org.junit.Test

class LayoutTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun load_displayItems_itemsExist() {
        composeTestRule.setContent {
            FetchItemListScreen(
                items = listOf(
                    FetchItem(1, 1, "Test1"),
                    FetchItem(2, 1, "Test2"),
                    FetchItem(3, 2, "Test3"),
                    FetchItem(4, 2, "Test4"),
                )
            )
        }


        composeTestRule.onAllNodesWithTag("item_card")
            .apply {
                fetchSemanticsNodes()
                    .forEachIndexed { i, _ ->
                        val node = get(i)
                        node.assertIsDisplayed()
                    }
            }
    }
}