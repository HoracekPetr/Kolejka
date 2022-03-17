package com.example.kolejka.view.util.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ExperimentalGraphicsApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.kolejka.view.ui.screens.login_screen.LoginScreen
import com.example.kolejka.view.ui.screens.register_screen.RegisterScreen
import com.example.kolejka.view.ui.screens.splash_screen.SplashScreen
import com.example.kolejka.view.util.Screen
import com.example.kolejka.view.util.navigation.app_holder.AppHolder
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalFoundationApi
@ExperimentalGraphicsApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun MainNavHost() {

    val mainNavController = rememberNavController()
    
    NavHost(navController = mainNavController, startDestination = Screen.SplashScreen.route){
        composable(route = Screen.SplashScreen.route){
            SplashScreen(navController = mainNavController)
        }

        composable(route = Screen.LoginScreen.route) {
            LoginScreen(navController = mainNavController)
        }

        composable(
            route = Screen.RegisterScreen.route
        ) {
            RegisterScreen(navController = mainNavController)
        }

        composable(route = Screen.AppHolderScreen.route){
            val appNavController = rememberNavController()
            AppHolder(navController = appNavController, secondaryNavController = mainNavController)
        }


    }
    
}