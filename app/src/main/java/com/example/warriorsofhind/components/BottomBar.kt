package com.example.warriorsofhind.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun BottomBar(modifier: Modifier = Modifier, onClick: () -> Unit) {
    BottomAppBar(
        modifier = modifier
    ) {
        Row(
            modifier = modifier.fillMaxSize()
        ) {
            IconButton(onClick = {
                onClick()
            }) {
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = null,
                    tint = Color.Red
                )
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Filled.Share, contentDescription = null)
            }
        }

    }
}