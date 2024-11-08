package com.example.fetchtest.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.fetchtest.FetchApplication
import com.example.fetchtest.data.FetchItemRepository
import com.example.fetchtest.model.FetchItem
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class HomeViewModel(private val fetchItemRepository: FetchItemRepository) : ViewModel() {
    var uiState: HomeUiState by mutableStateOf(HomeUiState.Loading) //Default to "Loading" state
        private set

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as FetchApplication)
                val fetchItemRepository =
                    application.container.fetchItemRepository //Get application repository instance
                HomeViewModel(fetchItemRepository)  //initialize ViewModel
            }
        }
    }

    init {
        getItems()  //Get items on screen load
    }

    fun getItems() {
        viewModelScope.launch {
            uiState =
                HomeUiState.Loading   //Set to Loading (in case it isn't already) while the next operation completes
            uiState = try {
                val items = fetchItemRepository.getItems()  //Get all items
                    .filter { !it.name.isNullOrBlank() }    //Exclude null/blank name items
                    .sortedWith(                            //Sort by listId then name
                        comparator = compareBy({ it.listId }, { it.name })
                    )
                HomeUiState.Success(items)
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }
}

/**
 * State holder
 */
sealed interface HomeUiState {
    data class Success(val items: List<FetchItem>) : HomeUiState
    object Error : HomeUiState
    object Loading : HomeUiState
}