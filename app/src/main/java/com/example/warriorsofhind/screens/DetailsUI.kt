package com.example.warriorsofhind.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.warriorsofhind.models.WarriorsItem
import com.example.warriorsofhind.network.NetworkStatusWrapper
import com.example.warriorsofhind.viewmodel.WarriorsDataViewModel

@Composable
fun DetailsMainScreen() {
    val viewModel: WarriorsDataViewModel = hiltViewModel()
    val data = viewModel.warriorsDataListState.observeAsState()

    when (data.value) {
        is NetworkStatusWrapper.Success<*> -> {
            DetailsScreen(data = data.value!!)
        }

        is NetworkStatusWrapper.Loading<*> -> {
            ShowLoading(loading = "Loading...")
        }

        else -> {
            ShowError(error = "Error 404")
        }
    }
}

@Composable
fun ShowLoading(loading: String) {
    Text(text = loading)
}

@Composable
fun ShowError(error: String) {
    Text(text = error)
}

@Composable
fun DetailsScreen(data: NetworkStatusWrapper<List<WarriorsItem>>) {

    val details = data.data

    LazyColumn(content = {
        items(details!!) {
            DetailsCard(data = it.about)
        }
    })

}

@Composable
fun DetailsCard(data: String) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        border = BorderStroke(1.dp, Color(0xFFCCCCCC)),
        content = {
            Text(
                text = data,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    )

}