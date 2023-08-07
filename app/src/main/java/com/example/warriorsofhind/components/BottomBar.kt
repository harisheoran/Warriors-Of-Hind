package com.example.warriorsofhind.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.warriorsofhind.utils.Favourites
import com.example.warriorsofhind.utils.Pager

@Composable
fun BottomBar(modifier: Modifier = Modifier, onClick: (route: String) -> Unit) {
    BottomAppBar(
        modifier = modifier
    ) {
        Row(
            modifier = modifier.fillMaxSize()
        ) {
            IconButton(onClick = {
                onClick(Favourites.route)
            }) {
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = null,
                    tint = Color.Red
                )
            }
            IconButton(onClick = {
                onClick(Pager.route)
            }) {
                Icon(Icons.Filled.Face, contentDescription = null)
            }
        }

    }
}


@Composable
fun MyBottomBar() {

}