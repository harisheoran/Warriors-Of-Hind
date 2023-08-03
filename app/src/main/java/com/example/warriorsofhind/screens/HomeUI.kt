package com.example.warriorsofhind.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.warriorsofhind.R
import com.example.warriorsofhind.components.HomeCard
import com.example.warriorsofhind.components.UiStatus
import com.example.warriorsofhind.models.King
import com.example.warriorsofhind.network.NetworkStatusWrapper
import com.example.warriorsofhind.viewmodel.WarriorsNameViewModel

@Composable
fun HomeScreen(onClick: (args: String) -> Unit) {
    val viewModel: WarriorsNameViewModel = hiltViewModel()
    val names = viewModel.warriorsNameListState.observeAsState()

    when (val namesList = names.value) {
        is NetworkStatusWrapper.Success<*> -> {
            HomeUI(
                onClick = onClick,
                onClickFavourite = {
                    viewModel.saveFavouriteKing(it)
                },
                names = namesList!!
            )
        }

        is NetworkStatusWrapper.Loading<*> -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                UiStatus(R.raw.loading)
            }
        }

        else -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                UiStatus(R.raw.server_error)
            }
        }
    }
}

@Composable
fun HomeUI(
    onClick: (args: String) -> Unit,
    onClickFavourite: (favouriteKing: King) -> Unit,
    names: NetworkStatusWrapper<List<King>>
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(
            start = 12.dp,
            top = 16.dp,
            end = 12.dp,
            bottom = 16.dp
        ),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
        //verticalArrangement = Arrangement.SpaceAround,
    ) {
        val namesList = names?.data ?: emptyList()
        items(namesList) {
            HomeCard(
                king = it,
                onClick = onClick,
                onClickFavourite = onClickFavourite
            )
        }
    }
}
