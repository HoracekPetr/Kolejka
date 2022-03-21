package com.example.kolejka.view.util.navigation.app_holder

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PostAdd
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ExperimentalGraphicsApi
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.kolejka.R
import com.example.kolejka.view.ui.components.bottom_navigation.BottomNavigationBar
import com.example.kolejka.view.ui.components.bottom_navigation.FloatingAddPostButton
import com.example.kolejka.view.util.AppNavigationHost
import com.example.kolejka.view.util.Screen
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.flow.collect

@ExperimentalFoundationApi
@ExperimentalGraphicsApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun AppHolder(
    navController: NavHostController,
    secondaryNavController: NavHostController,
    viewModel: AppHolderViewModel = hiltViewModel()
) {
    
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val screensWithAddPostButton = listOf(
        Screen.PostScreen.route
    )

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                onItemClick = {
                    if (navBackStackEntry?.destination?.route != it.route) navController.navigate(
                        it.route
                    ) {
                        launchSingleTop = true
                        popUpTo(Screen.PostScreen.route) {
                            saveState = true
                        }
                    }

                    if(navBackStackEntry?.destination?.route == Screen.NotificationScreen.route){
                        viewModel.setNotificationsToZero()
                    }
                },
                notificationsCount = viewModel.notificationsCount.value
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
        }) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            AppNavigationHost(
                navController = navController,
                secondaryNavController = secondaryNavController
            )
        }
    }
}