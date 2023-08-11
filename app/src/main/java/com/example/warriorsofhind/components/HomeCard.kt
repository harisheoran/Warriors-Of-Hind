package com.example.warriorsofhind.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.warriorsofhind.R
import com.example.warriorsofhind.models.King

@Composable
fun HomeCard(
    king: King,
    onClick: (args: String, img: String) -> Unit,
    onClickFavourite: (favouriteKing: King) -> Unit,
) {
    // Create a Composable state wrapper for isFavorite
    var isFavouriteState = rememberUpdatedState(king.isFavourite)
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick(king.name, king.img)
            },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column() {
            HomeCardImage(imgUrl = king.img)
            Row() {
                Text(
                    text = king.name,
                    modifier = Modifier.padding(
                        start = 8.dp,
                        end = 8.dp,
                        top = 6.dp,
                        bottom = 12.dp
                    ),
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
fun HomeCardImage(imgUrl: String) {
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imgUrl)
            .diskCacheKey("halloween_image_$imgUrl")
            .error(R.drawable.error_img)
            .build(),
        modifier = Modifier
            .width(250.dp)
            .height(320.dp)
            .clip(shape = RoundedCornerShape(size = 8.dp)),
        loading = {
            CircularProgressIndicator()
        },
        contentScale = ContentScale.Crop,
        contentDescription = "Warriors Image"
    )
}