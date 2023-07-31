package com.example.warriorsofhind

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.warriorsofhind.components.MyAppBar
import com.example.warriorsofhind.network.WarriorService
import com.example.warriorsofhind.screens.DetailsCard
import com.example.warriorsofhind.screens.DetailsMainScreen
import com.example.warriorsofhind.screens.DetailsScreen
import com.example.warriorsofhind.screens.HomeScreen
import com.example.warriorsofhind.ui.theme.WarriorsOfHindTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WarriorsOfHindTheme {
                Surface(
                    color = MaterialTheme.colorScheme.surface,

                    ) {
                    val navController = rememberNavController()
                    //  val backStackEntry by navController.currentBackStackEntryAsState()
                    val canNavigateUp: Boolean = navController.previousBackStackEntry != null
                    Scaffold(
                        topBar = {
                            MyAppBar(
                                canNavigateUp = canNavigateUp,
                                navigateUp = { navController.navigateUp() }
                            )
                        }
                    ) {
                        MyApp(modifier = Modifier.padding(it), navController = navController)
                    }
                }
            }
        }

    }
}

@Composable
fun MyApp(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {

    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier
    ) {
        composable(route = "home") {
            HomeScreen() { args ->
                navController.navigate("details/${args}") {
                    launchSingleTop = true
                }
            }
        }

        composable(
            route = "details/{kingName}",
            arguments = listOf(
                navArgument(name = "kingName") {
                    type = NavType.StringType
                }
            )
        ) {
            DetailsMainScreen()
        }
    }
}
