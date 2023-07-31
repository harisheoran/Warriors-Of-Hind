package com.example.warriorsofhind.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
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
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.SpaceAround,
    ) {
        val namesList = names?.data ?: emptyList()
        items(namesList) {
            HomeCard(name = it.name, img = it.img, onClick = onClick)
        }
    }
}

@Composable
fun HomeCard(name: String, img: String, onClick: (args: String) -> Unit) {
    Box(
        modifier = Modifier
            .padding()
            .size(160.dp)
            .clip(RoundedCornerShape(8.dp))
            .paint(
                painter = painterResource(id = R.drawable.card_bg),
                contentScale = ContentScale.Crop
            )
            .border(1.dp, color = Color(0xFFEEEEEE))
            .clickable {
                onClick(name)
            },
        contentAlignment = Alignment.BottomCenter
    ) {
        Column {
            HomeCardImage(img)
            Text(
                text = name,
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier.padding(vertical = 20.dp, horizontal = 0.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
fun HomeCardImage(imgUrl: String) {
    AsyncImage(
        model = imgUrl,
        contentDescription = "This is an example image"
    )
}


