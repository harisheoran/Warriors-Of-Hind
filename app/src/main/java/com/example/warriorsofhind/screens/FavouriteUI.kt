package com.example.warriorsofhind.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.warriorsofhind.components.HomeCard
import com.example.warriorsofhind.viewmodel.WarriorsNameViewModel

@Composable
fun FavouriteScreen(modifier: Modifier = Modifier, onClick: (arg: String) -> Unit) {
    val viewModel: WarriorsNameViewModel = hiltViewModel()
    val favouritesState = viewModel.favourites.observeAsState()

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
        val favouriteList = favouritesState.value ?: emptyList()
        items(favouriteList) { king ->
            HomeCard(
                king = king,
                onClick = { onClick(king.name) },
                onClickFavourite = {}
            )
        }
    }

}
