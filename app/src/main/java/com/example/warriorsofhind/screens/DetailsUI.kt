package com.example.warriorsofhind.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.warriorsofhind.R
import com.example.warriorsofhind.components.DetailsCard
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