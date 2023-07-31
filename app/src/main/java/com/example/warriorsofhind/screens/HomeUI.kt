package com.example.warriorsofhind.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.warriorsofhind.R
import com.example.warriorsofhind.models.King
import com.example.warriorsofhind.network.NetworkStatusWrapper
import com.example.warriorsofhind.viewmodel.WarriorsNameViewModel

@Composable
fun HomeScreen(onClick: (args: String) -> Unit) {
    val viewModel: WarriorsNameViewModel = hiltViewModel()
    val names = viewModel.warriorsNameListState.observeAsState()

    when (val namesList = names.value) {
        is NetworkStatusWrapper.Success<*> -> {
            HomeUI(onClick = onClick, names = namesList!!)
        }

        is NetworkStatusWrapper.Loading<*> -> {
            ShowLoading(loading = "Loading...")
        }

        else -> {
            ShowError(error = "Errror 404")
        }
    }
}

@Composable
fun HomeUI(onClick: (args: String) -> Unit, names: NetworkStatusWrapper<List<King>>) {
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
        val namesList = names?.data ?: emptyList()
        items(namesList) {
            HomeCard(name = it.name, img = it.img, onClick = onClick)
        }
    }
}

@Composable
fun HomeCard(name: String, img: String, onClick: (args: String) -> Unit) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick(name)
            },
    ) {

        Column() {
            HomeCardImage(imgUrl = img)

            Text(
                text = name,
                modifier = Modifier.padding(start = 12.dp, top = 12.dp, bottom = 16.dp),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
    }
}

@Composable
fun HomeCardImage(imgUrl: String) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imgUrl)
            .build(),
        modifier = Modifier
            .width(250.dp)
            .clip(shape = RoundedCornerShape(size = 12.dp)),
        contentScale = ContentScale.Crop,
        contentDescription = "This is an example image"
    )
}

@Preview
@Composable
fun previewHomeCard() {
    HomeCard(name = "Surjmal", img = "", onClick = {})
}

