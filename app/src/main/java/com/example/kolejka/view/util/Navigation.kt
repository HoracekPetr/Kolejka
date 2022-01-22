package com.example.kolejka.view.util

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ExperimentalGraphicsApi
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.kolejka.models.Post
import com.example.kolejka.view.ui.screens.login_screen.LoginScreen
import com.example.kolejka.view.ui.screens.main_post_screen.PostScreen
import com.example.kolejka.view.ui.screens.new_post_screen.NewPostScreen
import com.example.kolejka.view.ui.screens.notification_screen.NotificationScreen
import com.example.kolejka.view.ui.screens.post_detail_screen.PostDetailScreen
import com.example.kolejka.view.ui.screens.profile_screen.ProfileScreen
import com.example.kolejka.view.ui.screens.register_screen.RegisterScreen
import com.example.kolejka.view.ui.screens.splash_screen.SplashScreen
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalGraphicsApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun Navigation(navController: NavHostController) {

    NavHost(navController = navController, startDestination = Screen.SplashScreen.route) {

        composable(route = Screen.SplashScreen.route){
            SplashScreen(navController = navController)
        }

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
            route = Screen.ProfileScreen.route,
        ) {
            ProfileScreen(navController = navController)
        }

        composable(
            route = Screen.PostDetailScreen.route + "?postId={postId}",
            arguments = listOf(
                navArgument(name = "postId"){
                    nullable = true
                    defaultValue = null
                    type = NavType.StringType
                }
            )
        ) {
            val postId = it.arguments?.getString("postId")
            println("POST ID: $postId")
            PostDetailScreen(
                navController = navController,
                postId = postId
            )
        }

        composable(
            route = Screen.NewPostScreen.route
        ) {
            NewPostScreen(navController = navController)
        }
    }
}