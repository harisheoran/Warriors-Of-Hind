package com.example.warriorsofhind.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.warriorsofhind.R
import com.example.warriorsofhind.components.UiStatus
import com.example.warriorsofhind.firebase.FirebaseViewModel
import com.example.warriorsofhind.firebase.STATUS
import com.example.warriorsofhind.models.Wallpaper

@Composable
fun WallpaperScreen(onClick: (img: String) -> Unit) {
    val viewModel: FirebaseViewModel = hiltViewModel()
    val wallpapers = viewModel.wallpaper.observeAsState()
    val wallpapersList = wallpapers.value

    val status = viewModel.status.observeAsState()
    val statusValue = status.value

    when (statusValue) {
        STATUS.SUCCESS -> {
            WallpaperUI(wallpapersList, onClick = onClick)
        }

        STATUS.LOADING -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.TopCenter
            ) {
                UiStatus(lottieFile = R.raw.loading_ui2)
            }
        }

        else -> {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.TopCenter
            ) {
                UiStatus(lottieFile = R.raw.server_error)
            }

        }
    }
}


@Composable
fun WallpaperUI(wallpaper: List<Wallpaper>?, onClick: (img: String) -> Unit) {
    if (wallpaper != null) {
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
        ) {
            items(wallpaper) {
                WallpaperCardImage(it, onClick)
            }
        }
    }
}


@Composable
fun WallpaperCardImage(wallpaper: Wallpaper, onClick: (img: String) -> Unit) {
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(wallpaper.img)
            .diskCacheKey("wallpaper_${wallpaper.name}")
            .error(R.drawable.error_img)
            .build(),
        modifier = Modifier
            .width(250.dp)
            .height(320.dp)
            .clip(shape = RoundedCornerShape(size = 8.dp))
            .clickable {
                onClick(wallpaper.img!!)
            },
        loading = {
            CircularProgressIndicator()
        },
        contentScale = ContentScale.Crop,
        contentDescription = "Warriors Image"
    )
}
