package com.example.warriorsofhind

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.warriorsofhind.components.BottomNavigationBar
import com.example.warriorsofhind.components.MyAppBar
import com.example.warriorsofhind.screens.DetailsMainScreen
import com.example.warriorsofhind.screens.FavouriteScreen
import com.example.warriorsofhind.screens.HomeScreen
import com.example.warriorsofhind.screens.PagerScreen
import com.example.warriorsofhind.ui.theme.WarriorsOfHindTheme
import com.example.warriorsofhind.utils.Destinations
import com.example.warriorsofhind.utils.Details
import com.example.warriorsofhind.utils.Favourites
import com.example.warriorsofhind.utils.Home
import com.example.warriorsofhind.utils.Pager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WarriorsOfHindTheme {
                Surface(
                    tonalElevation = 5.dp,
                ) {
                    MyApp()
                }
            }
        }
    }
}

enum class LunchTrayScreen(val title: String) {
    Start(title = "Home"),
    Entree(title = "Jassa"),
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = backStackEntry?.arguments?.getString("kingName") ?: Home.label
    val currentRoute = backStackEntry?.destination?.route
    val items = listOf<Destinations>(Home, Favourites, Pager)

    Scaffold(
        topBar = {
            MyAppBar(
                currentScreen = currentScreen,
                canNavigateUp = navController.previousBackStackEntry != null
            ) { navController.navigateUp() }
        },
        bottomBar = {
            if (currentRoute != null) {
                BottomNavigationBar(
                    currentRoute = currentRoute,
                    onClickNavigate = { arg ->
                        navController.navigate(arg) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    items = items
                )
            }
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = Home.route,
            modifier = modifier.padding(it)
        ) {
            composable(route = Home.route) {
                HomeScreen() { arg ->
                    navController.navigate("${Details.route}/$arg") {
                        launchSingleTop = true
                    }
                }
            }

            composable(
                route = Details.argWithRoute,
                arguments = Details.argument
            ) {
                DetailsMainScreen()
            }

            composable(route = Favourites.route) {
                FavouriteScreen(
                    onClick = { arg ->
                        navController.navigate("${Details.route}/$arg") {

                            launchSingleTop = true
                        }
                    }
                )
            }

            composable(route = Pager.route) {
                PagerScreen(
                    onClick = { arg ->
                        navController.navigate("${Details.route}/$arg") {
                            launchSingleTop = true

                        }
                    }
                )
            }
        }

    }
}