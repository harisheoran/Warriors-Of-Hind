package com.example.warriorsofhind.screens

import android.annotation.SuppressLint
import androidx.annotation.RawRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.warriorsofhind.R
import com.example.warriorsofhind.components.UiStatus
import com.example.warriorsofhind.models.WarriorsItem
import com.example.warriorsofhind.network.NetworkStatusWrapper
import com.example.warriorsofhind.viewmodel.WarriorsDataViewModel

@SuppressLint("SupportAnnotationUsage")
@Composable
fun DetailsMainScreen() {
    val viewModel: WarriorsDataViewModel = hiltViewModel()
    val data = viewModel.warriorsDataListState.observeAsState()

    when (data.value) {
        is NetworkStatusWrapper.Success<*> -> {
            DetailsScreen(data = data.value!!)
        }

        is NetworkStatusWrapper.Loading<*> -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter

            ) {

                UiStatus(lottieFile = R.raw.loading_ui2)

            }
        }

        else -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                UiStatus(lottieFile = R.raw.server_error)
            }
        }
    }
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