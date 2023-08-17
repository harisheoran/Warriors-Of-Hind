package com.example.warriorsofhind.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.warriorsofhind.R


@Composable
fun WallpaperViewScreen(img: String, name: String) {
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
            WallpaperFullScreenView(img = img)
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
            Text(
                text = name,
                style = MaterialTheme.typography.titleLarge.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                ),
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .background(Color.Black.copy(alpha = 0.5f))
                    .padding(start = 8.dp, bottom = 8.dp, end = 8.dp)
                    .wrapContentSize()

            )
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