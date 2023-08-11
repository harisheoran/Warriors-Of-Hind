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
        get() = "Warriors Of Hind"
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

object Pager : Destinations {
    override val route: String
        get() = "pager"
    override val icon: Int
        get() = R.drawable.view_carousel

    override val label: String
        get() = "Pager"

}
