@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.fetchtest.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fetchtest.R
import com.example.fetchtest.ui.screens.HomeScreen
import com.example.fetchtest.ui.screens.HomeViewModel

@Composable
fun FetchApp(modifier: Modifier = Modifier) {
    val behavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(behavior.nestedScrollConnection),
        topBar = { FetchTopAppBar(scrollBehavior = behavior) }
    ) {
        Surface(modifier = modifier.fillMaxSize()) {
            val homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)
            HomeScreen(
                modifier = Modifier,
                homeUiState = homeViewModel.uiState,
                retryAction = homeViewModel::getItems,
                contentPadding = it
            )
        }
    }
}

@Composable
fun FetchTopAppBar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = stringResource(R.string.title),
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White
            )
        }
    )
}