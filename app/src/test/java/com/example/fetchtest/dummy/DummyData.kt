package com.example.fetchtest.dummy

import com.example.fetchtest.model.FetchItem

object DummyData {
    private const val id1 = 888
    private const val id2 = 984
    private const val id3 = 435
    private const val id4 = 12
    private const val listId1 = 3
    private const val listId2 = 2
    private const val listId3 = 4
    private const val listId4 = 1
    private const val name1 = "Name A"
    private const val name2 = "Name B"
    private const val name3 = ""
    private val name4: String? = null
    val itemList = listOf(
        FetchItem(id1, listId1, name1),
        FetchItem(id2, listId2, name2),
        FetchItem(id3, listId3, name3),
        FetchItem(id4, listId4, name4),
    )
        .filter { !it.name.isNullOrBlank() }    //Exclude null/blank name items
        .sortedWith(                            //Sort by listId then name
            comparator = compareBy({ it.listId }, { it.name })
        )
}