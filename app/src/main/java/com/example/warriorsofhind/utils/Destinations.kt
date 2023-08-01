package com.example.warriorsofhind.utils

import androidx.annotation.StringRes
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.warriorsofhind.R
import com.example.warriorsofhind.utils.Details.route

enum class Destinat() {


}


class Route(val route: String, val label: String)

interface Destinations {
    val route: String
}


object Home : Destinations {
    override val route: String
        get() = "home"

    val label: String = "Warriors Of Hind"
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
}
