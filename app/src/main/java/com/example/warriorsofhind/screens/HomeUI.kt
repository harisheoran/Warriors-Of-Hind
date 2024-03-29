package com.example.warriorsofhind.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.warriorsofhind.R
import com.example.warriorsofhind.components.HomeCard
import com.example.warriorsofhind.components.UiStatus
import com.example.warriorsofhind.models.King
import com.example.warriorsofhind.network.ApiResponse
import com.example.warriorsofhind.viewmodel.WarriorsNameViewModel

@Composable
fun HomeScreen(onClick: (args: String, img: String) -> Unit) {
    val viewModel: WarriorsNameViewModel = hiltViewModel()
    // val names = viewModel.warriorsNameListState.observeAsState()

    val warriorsState = viewModel.warriorsStateFlow.collectAsState()
    val apiRes = warriorsState.value

    var selected by rememberSaveable {
        mutableStateOf(false)
    }


    when (apiRes.status) {

        // Successfull response
        is ApiResponse.Status.Success -> {

            HomeUI(
                onClick = onClick,
                onClickFavourite = {
                    viewModel.saveFavouriteKing(it)
                },
                warriors = apiRes
            )
        }

        // Failure Response
        is ApiResponse.Status.Failure -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                UiStatus(R.raw.server_error)
            }
        }

        // Loading
        else -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                UiStatus(R.raw.loading)
            }
        }
    }

}

@Composable
fun HomeUI(
    onClick: (args: String, img: String) -> Unit,
    onClickFavourite: (favouriteKing: King) -> Unit,
    warriors: ApiResponse<List<King>>
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        contentPadding = PaddingValues(
            start = 16.dp,
            top = 16.dp,
            end = 16.dp,
            bottom = 16.dp
        ),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val warriorsData = warriors.dataBody
        items(warriorsData) {
            HomeCard(
                king = it,
                onClick = onClick,
                onClickFavourite = onClickFavourite
            )
        }
    }
}
