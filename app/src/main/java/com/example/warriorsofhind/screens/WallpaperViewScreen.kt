package com.example.warriorsofhind.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.warriorsofhind.R


@Composable
fun WallpaperViewScreen(img: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(2.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(0.dp)
        ) {
            WallpaperFullScreenView(img = "https://i.imgur.com/Hg2chMP.jpg")
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .align(Alignment.BottomEnd)

            ) {
                Icon(
                    painter = painterResource(id = R.drawable.download),
                    contentDescription = "download button"
                )
            }

        }
    }
}

@Composable
fun WallpaperFullScreenView(img: String) {
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(img)
            .diskCacheKey("wallpaper_${img}")
            .error(R.drawable.error_img)
            .build(),
        modifier = Modifier
            .fillMaxSize()
            .clip(shape = RoundedCornerShape(size = 0.dp)),
        loading = {
            CircularProgressIndicator()
        },
        contentScale = ContentScale.Crop,
        contentDescription = "Wallpaper"
    )
}