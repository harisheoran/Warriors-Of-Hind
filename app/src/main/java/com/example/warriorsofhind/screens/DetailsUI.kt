package com.example.warriorsofhind.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.warriorsofhind.R
import com.example.warriorsofhind.components.UiStatus
import com.example.warriorsofhind.models.WarriorsItem
import com.example.warriorsofhind.network.ApiResponse
import com.example.warriorsofhind.viewmodel.WarriorsDataViewModel

@SuppressLint("SupportAnnotationUsage")
@Composable
fun DetailsMainScreen(imgUrl: String) {
    val viewModel: WarriorsDataViewModel = hiltViewModel()
    // val data = viewModel.warriorsDataListState.observeAsState()

    val warriorsDetailsState = viewModel.warriorsDetailsStateFlow.collectAsState()
    val response = warriorsDetailsState.value

    when (response.status) {
        // SUCCESS
        is ApiResponse.Status.Success -> {
            DetailsUI(data = response.dataBody, imgUrl = imgUrl)
        }

        // FAILURE
        is ApiResponse.Status.Failure -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                UiStatus(lottieFile = R.raw.server_error)
            }
        }

        // LOADING
        else -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.TopCenter
            ) {
                UiStatus(lottieFile = R.raw.loading_ui2)
            }
        }
    }
}

@Composable
fun DetailsUI(data: List<WarriorsItem>, imgUrl: String) {

    var isBookmarked by rememberSaveable {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        LazyColumn(
            content = {
                items(data) {
                    DetailsHeader(it, isBookmarked, onClick = {
                        isBookmarked = !isBookmarked
                    })
                    Row() {
                        DetailsSamllInfoCard(info = it.tribe)
                        DetailsSamllInfoCard(info = it.period)
                    }
                    DetailsImage(imgUrl = imgUrl)
                    DetailsText(about = it.about)
                }
            }
        )
    }
}

@Composable
fun DetailsHeader(data: WarriorsItem, isBookmarked: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Text(
            text = data.name,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(start = 20.dp, top = 12.dp)
                .align(Alignment.TopStart)
        )

        Icon(
            painter = if (isBookmarked) {
                painterResource(id = R.drawable.bookmark_filled)
            } else {
                painterResource(id = R.drawable.bookmark_save)
            },
            contentDescription = null,
            modifier = Modifier
                .padding(end = 20.dp, top = 12.dp)
                .size(32.dp)
                .align(Alignment.TopEnd)
                .clickable(onClick = onClick)
        )

    }
}

@Composable
fun DetailsText(about: String) {
    Text(
        text = "About",
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 12.dp),
    )
    Text(
        text = about,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 12.dp)
    )
}

@Composable
fun DetailsSamllInfoCard(info: String) {
    Card(
        modifier = Modifier.padding(start = 20.dp, top = 12.dp, bottom = 12.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        shape = MaterialTheme.shapes.extraLarge,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Text(
            text = if (info == "") {
                "null"
            } else {
                info
            },
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color.Black,
                fontWeight = FontWeight.Bold
            ),
            color = Color.Black,
            modifier = Modifier
                .background(Color.White.copy(alpha = 0.4f))
                .padding(
                    start = 12.dp,
                    end = 12.dp,
                    top = 2.dp,
                    bottom = 2.dp
                )
        )
    }
}


@Composable
fun DetailsImage(
    modifier: Modifier = Modifier,
    imgUrl: String
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp)
    ) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(imgUrl)
                .error(R.drawable.error_img)
                .build(),
            modifier = Modifier
                .width(250.dp)
                .height(250.dp)
                .align(Alignment.Center)
                .clip(shape = RoundedCornerShape(size = 180.dp)),
            loading = {
                CircularProgressIndicator()
            },
            contentScale = ContentScale.Crop,
            contentDescription = "Warriors Image"
        )
    }
}