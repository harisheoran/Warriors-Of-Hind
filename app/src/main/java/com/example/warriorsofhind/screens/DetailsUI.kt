package com.example.warriorsofhind.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.warriorsofhind.R
import com.example.warriorsofhind.components.DetailsCard
import com.example.warriorsofhind.components.UiStatus
import com.example.warriorsofhind.models.WarriorsItem
import com.example.warriorsofhind.network.ApiResponse
import com.example.warriorsofhind.viewmodel.WarriorsDataViewModel

@SuppressLint("SupportAnnotationUsage")
@Composable
fun DetailsMainScreen() {
    val viewModel: WarriorsDataViewModel = hiltViewModel()
    // val data = viewModel.warriorsDataListState.observeAsState()

    val warriorsDetailsState = viewModel.warriorsDetailsStateFlow.collectAsState()
    val response = warriorsDetailsState.value

    Log.d("WARRIOR DETAILS", response.toString())

    when (response.status) {
        // SUCCESS
        is ApiResponse.Status.Success -> {
            DetailsScreen(response.dataBody)
        }

        // FAILURE
        is ApiResponse.Status.Failure -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                UiStatus(lottieFile = R.raw.server_error)
            }
        }

        // LOADING
        else -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {
                UiStatus(lottieFile = R.raw.loading_ui2)
            }
        }
    }

    /*when (data.value) {
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
    }*/
}

@Composable
fun DetailsScreen(data: List<WarriorsItem>) {
    LazyColumn(content = {
        items(data) {
            DetailsCard(data = it.about)
        }
    })
}