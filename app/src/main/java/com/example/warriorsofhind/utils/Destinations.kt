package com.example.warriorsofhind.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument

enum class Destinat() {


}


class Route(val route: String, val label: String)

interface Destinations {
    val route: String
    val icon: ImageVector
    val label: String
}


object Home : Destinations {
    override val route: String
        get() = "home"
    override val icon: ImageVector
        get() = Icons.Filled.Home

    override val label: String
        get() = "Warriors Of Hind"
}

object Details : Destinations {

    override val route: String
        get() = "details"

    val arg: String = "kingName"

    val argWithRoute = "${route}/{${arg}}"

    val argument = listOf(
        navArgument(name = Details.arg) {
            type = NavType.StringType
        }
    )

    override val icon: ImageVector
        get() = Icons.Filled.Info

    override val label: String
        get() = "Details"
}

object Favourites : Destinations {
    override val route: String
        get() = "favourites"
    override val icon: ImageVector
        get() = Icons.Filled.Favorite

    override val label: String
        get() = "Favourites"
}

object Pager : Destinations {
    override val route: String
        get() = "pager"
    override val icon: ImageVector
        get() = Icons.Filled.Face

    override val label: String
        get() = "Pager"

}
