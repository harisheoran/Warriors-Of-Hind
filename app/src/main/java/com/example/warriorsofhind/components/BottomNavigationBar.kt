package com.example.warriorsofhind.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.warriorsofhind.utils.Destinations


@Composable
fun BottomNavigationBar(
    currentRoute: String,
    items: List<Destinations>,
    onClickNavigate: (arg: String) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(modifier = modifier) {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    onClickNavigate(item.route)
                },
                icon = {
                       Icon(painter = painterResource(id = item.icon), contentDescription = "${item.icon.toString()}",
                           modifier = Modifier.size(24.dp)
                           )
                },
                label = {
                    Text(text = item.label)
                },
                colors = NavigationBarItemDefaults.colors(
                    unselectedTextColor = Color.Gray, selectedTextColor = Color.Black
                ),
            )
        }
    }

}

