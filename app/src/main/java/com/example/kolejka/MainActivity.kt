package com.example.kolejka

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PostAdd
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ExperimentalGraphicsApi
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.kolejka.view.theme.KolejkaTheme
import com.example.kolejka.view.ui.components.bottom_navigation.BottomNavigationBar
import com.example.kolejka.view.ui.components.bottom_navigation.BottomNavItem
import com.example.kolejka.view.ui.components.bottom_navigation.FloatingAddPostButton
import com.example.kolejka.view.util.Navigation
import com.example.kolejka.view.util.Screen
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @ExperimentalGraphicsApi
    @ExperimentalMaterialApi
    @ExperimentalPagerApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bottomBarItems = listOf(
            BottomNavItem(
                name = getString(R.string.posts),
                route = Screen.PostScreen.route,
                Icons.Outlined.Menu
            ),
            BottomNavItem(
                name = getString(R.string.notifications),
                route = Screen.NotificationScreen.route,
                Icons.Outlined.Notifications,
                notificationCount = 0
            ),
            BottomNavItem(
                name = getString(R.string.profile),
                route = Screen.ProfileScreen.route,
                Icons.Outlined.Person
            )
        )

        val screensWithBottomBar = listOf(
            Screen.PostScreen.route,
            Screen.NotificationScreen.route,
            Screen.ProfileScreen.route
        )
        val screensWithAddPostButton = listOf(
            Screen.PostScreen.route
        )

        setContent {

            KolejkaTheme {
                Surface(
                    color = MaterialTheme.colors.background,
                    modifier = Modifier.fillMaxSize()
                ) {
                    val navController = rememberNavController()
                    val navBackStackEntry by navController.currentBackStackEntryAsState()

                    Scaffold(
                        bottomBar = {
                            BottomNavigationBar(
                                items = bottomBarItems,
                                navController = navController,
                                onItemClick = {
                                    if (navBackStackEntry?.destination?.route != it.route) navController.navigate(
                                        it.route
                                    )
                                },
                                showBottomBar = navBackStackEntry?.destination?.route in screensWithBottomBar
                            )
                        },
                        floatingActionButton = {
                            FloatingAddPostButton(
                                showButton = navBackStackEntry?.destination?.route in screensWithAddPostButton,
                                buttonIcon = Icons.Default.PostAdd,
                                buttonText = stringResource(id = R.string.add_new_post),
                                iconDescription = stringResource(id = R.string.add_new_post),
                                onButtonClick = { navController.navigate(Screen.NewPostScreen.route) }
                            )
                        }
                    ) { innerPadding ->
                        Box(modifier = Modifier.padding(innerPadding)) {
                            Navigation(navController)
                        }
                    }

                }
            }
        }
    }
}


