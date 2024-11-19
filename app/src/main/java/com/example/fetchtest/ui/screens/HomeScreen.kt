package com.example.fetchtest.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fetchtest.R
import com.example.fetchtest.model.FetchItem
import com.example.fetchtest.ui.theme.FetchTestTheme

/**
 * Home screen composable dictates which screen is shown according to UI state
 * @param retryAction Action for trying the network connection again
 */
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeUiState: HomeUiState,
    retryAction: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    //Display differently for different UI states
    when (homeUiState) {
        is HomeUiState.Loading -> LoadingScreen(
            modifier = modifier,
            contentPadding = contentPadding
        )

        is HomeUiState.Success -> FetchItemListScreen(
            modifier = modifier,
            items = homeUiState.items,
            contentPadding = contentPadding
        )

        is HomeUiState.Error -> ErrorScreen(
            modifier = modifier,
            retryAction = retryAction,
            contentPadding = contentPadding
        )
    }
}

@Composable
fun FetchItemListScreen(
    modifier: Modifier = Modifier,
    items: List<FetchItem> = emptyList(),
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    if (items.isEmpty()) {  //If empty list, give a message so they know the operation completed
        Text(
            text = stringResource(R.string.no_items_found),
            style = MaterialTheme.typography.bodyMedium
        )
        //TODO: Add a graphic
    } else {
        LazyColumn(
            modifier = modifier
                .consumeWindowInsets(contentPadding)
                .background(color = MaterialTheme.colorScheme.background),
            contentPadding = contentPadding
        ) {
            items(items = items) { item ->
                ItemCard(item = item)
            }
        }
    }

}

@Composable
fun ItemCard(modifier: Modifier = Modifier, item: FetchItem) {
    Card(
        modifier = modifier
            .testTag("item_card")
            .fillMaxWidth()
            .padding(
                horizontal = dimensionResource(R.dimen.padding_large),
                vertical = dimensionResource(R.dimen.padding_medium)
            ),
        shape = MaterialTheme.shapes.small,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            /*  Would normally deserialize listId into an enum with Gson
             *  to assign attributes so that this wouldn't be necessary here. */
            val color = when (item.listId) {
                1 -> android.R.color.holo_purple
                2 -> android.R.color.holo_green_dark
                3 -> android.R.color.holo_orange_dark
                else -> android.R.color.holo_blue_dark
            }
            Text(
                modifier = Modifier
                    .width(64.dp)
                    .padding(all = dimensionResource(R.dimen.padding_small)),
                textAlign = TextAlign.Center,
                text = item.listId.toString(),
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Bold,
                color = colorResource(color)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(all = dimensionResource(R.dimen.padding_small)),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_small)),
                    text = "ID: ${item.id}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_small)),
                    text = "Name: ${item.name}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    Column(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .consumeWindowInsets(contentPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_medium)),
            text = stringResource(R.string.loading_please_wait),
            style = MaterialTheme.typography.bodyLarge
        )
        CircularProgressIndicator(
            modifier = Modifier.width(64.dp),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )
    }
}

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier, retryAction: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .consumeWindowInsets(contentPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_large)),
            text = "There was an error retrieving these items",
            color = colorResource(android.R.color.holo_red_dark),
            style = MaterialTheme.typography.bodyMedium
        )
        Button(
            modifier = Modifier
                .height(64.dp)
                .width(64.dp),
            shape = CircleShape,
            onClick = { retryAction() },
            contentPadding = PaddingValues(0.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Refresh,
                contentDescription = stringResource(R.string.retry)
            )
        }
    }

}

@Composable
@Preview
fun ItemListPreview() {
    FetchTestTheme {
        FetchItemListScreen(
            items = listOf(
                FetchItem(888, 3, "Name A"),
                FetchItem(984, 2, "Name B"),
                FetchItem(543, 1, "Name C")
            )
        )
    }
}

@Composable
@Preview
fun ItemCardPreview() {
    FetchTestTheme {
        ItemCard(item = FetchItem(888, 4, "Name"))
    }
}

@Composable
@Preview
fun LoadingPreview() {
    FetchTestTheme {
        LoadingScreen()
    }
}

@Composable
@Preview
fun ErrorPreview() {
    FetchTestTheme {
        ErrorScreen(retryAction = {})
    }
}