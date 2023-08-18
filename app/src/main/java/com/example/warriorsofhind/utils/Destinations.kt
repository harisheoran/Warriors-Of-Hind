package com.example.warriorsofhind.utils

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.warriorsofhind.R


interface Destinations {
    val route: String
    val icon: Int
    val label: String
}

object Home : Destinations {
    override val route: String
        get() = "home"
    override val icon: Int
        get() = R.drawable.sword
    override val label: String
        get() = "Warriors"
}

object Details : Destinations {

    override val route: String
        get() = "details"

    val arg: String = "kingName"

    val imgUrl: String = "img"

    val argWithRoute = "${Details.route}?kingName={${arg}},img={${imgUrl}}"

    val argument = listOf(
        navArgument(name = Details.arg) {
            type = NavType.StringType
        },
        navArgument(name = Details.imgUrl) {
            type = NavType.StringType
        }
    )

    override val icon: Int
        get() = R.drawable.sword

    override val label: String
        get() = "Details"
}

object Favourites : Destinations {
    override val route: String
        get() = "favourites"
    override val icon: Int
        get() = R.drawable.bookmark

    override val label: String
        get() = "Bookmarks"
}



object WallpaperRoute : Destinations {
    override val route: String
        get() = "wallpaper"
    override val icon: Int
        get() = R.drawable.wallpaper
    override val label: String
        get() = "Wallpaper"
}

object WallpaperView : Destinations {

    override val route: String
        get() = "wallview"
    override val icon: Int
        get() = R.drawable.wallpaper
    override val label: String
        get() = ""

    val argOne: String = "img"

    val argWithRoute =
        "${route}/{${argOne}}"

    val argument = listOf(
        navArgument(name = WallpaperView.argOne) {
            type = NavType.StringType
        },
    )
}