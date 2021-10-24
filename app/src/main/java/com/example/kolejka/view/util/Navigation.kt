package com.example.kolejka.view.util

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kolejka.view.ui.screens.login_screen.LoginScreen
import com.example.kolejka.view.ui.screens.register_screen.RegisterScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.LoginScreen.route ){
        composable(route = Screen.LoginScreen.route){
            LoginScreen(navController = navController)
        }

        composable(
            route = Screen.RegisterScreen.route
        ){
            RegisterScreen(navController = navController)
        }
    }
}