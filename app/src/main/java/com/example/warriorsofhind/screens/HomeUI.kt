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
fun HomeScreen(onClick: (args: String) -> Unit) {
    val viewModel: WarriorsNameViewModel = hiltViewModel()
   // val names = viewModel.warriorsNameListState.observeAsState()

    val warriors = viewModel.warriorsStateFlow.collectAsState()
    val apiRes = warriors.value

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

   /* when (val namesList = names.value) {
        is NetworkStatusWrapper.Success<*> -> {
        }

        is NetworkStatusWrapper.Loading<*> -> {
        }

        else -> {
        }
    }*/
}

@Composable
fun HomeUI(
    onClick: (args: String) -> Unit,
    onClickFavourite: (favouriteKing: King) -> Unit,
    warriors: ApiResponse<List<King>>
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
