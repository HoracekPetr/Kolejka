package com.example.kolejka.view.util

import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kolejka.view.ui.components.BottomNavigationBar
import com.example.kolejka.view.ui.components.bottom_navigation.BottomNavItem
import com.example.kolejka.view.ui.screens.login_screen.LoginScreen
import com.example.kolejka.view.ui.screens.main_post_screen.PostScreen
import com.example.kolejka.view.ui.screens.notification_screen.NotificationScreen
import com.example.kolejka.view.ui.screens.profile_screen.ProfileScreen
import com.example.kolejka.view.ui.screens.register_screen.RegisterScreen

@Composable
fun Navigation(navController: NavHostController) {

    NavHost(navController = navController, startDestination = Screen.LoginScreen.route) {
        composable(route = Screen.LoginScreen.route) {
            LoginScreen(navController = navController)
        }

        composable(
            route = Screen.RegisterScreen.route
        ) {
            RegisterScreen(navController = navController)
        }

        composable(
            route = Screen.PostScreen.route
        ) {
            PostScreen(navController = navController)
        }

        composable(
            route = Screen.NotificationScreen.route
        ) {
            NotificationScreen(navController = navController)
        }

        composable(
            route = Screen.ProfileScreen.route
        ) {
            ProfileScreen(navController = navController)
        }
    }


}