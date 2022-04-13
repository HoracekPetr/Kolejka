package com.example.kolejka.view.util

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ExperimentalGraphicsApi
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.kolejka.models.Post
import com.example.kolejka.view.ui.screens.edit_post_screen.EditPostScreen
import com.example.kolejka.view.ui.screens.login_screen.LoginScreen
import com.example.kolejka.view.ui.screens.main_post_screen.PostScreen
import com.example.kolejka.view.ui.screens.new_post_screen.NewPostScreen
import com.example.kolejka.view.ui.screens.news_detail_screen.NewsDetailScreen
import com.example.kolejka.view.ui.screens.news_screen.NewsScreen
import com.example.kolejka.view.ui.screens.notification_screen.NotificationScreen
import com.example.kolejka.view.ui.screens.post_detail_screen.PostDetailScreen
import com.example.kolejka.view.ui.screens.profile_screen.ProfileScreen
import com.example.kolejka.view.ui.screens.profile_screen.other_user_screen.OtherUserScreen
import com.example.kolejka.view.ui.screens.register_screen.RegisterScreen
import com.example.kolejka.view.ui.screens.splash_screen.SplashScreen
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalFoundationApi
@ExperimentalGraphicsApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun AppNavigationHost(navController: NavHostController, secondaryNavController: NavHostController) {

    NavHost(navController = navController, startDestination = Screen.PostScreen.route, route = "APP_ROOT") {

/*
        composable(route = Screen.LoginScreen.route) {
            LoginScreen(navController = navController)
        }
*/

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
            ProfileScreen(navController = navController, secondaryNavController = secondaryNavController)
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
            PostDetailScreen(
                navController = navController,
                postId = postId
            )
        }

        composable(
            route = Screen.EditPostScreen.route + "?postId={postId}",
            arguments = listOf(
                navArgument(name = "postId"){
                    nullable = true
                    defaultValue = null
                    type = NavType.StringType
                }
            )
        ) {
            val postId = it.arguments?.getString("postId")
            EditPostScreen(
                navController = navController,
                postId = postId
            )
        }

        composable(
            route = Screen.OtherUserScreen.route + "?userId={userId}",
            arguments = listOf(
                navArgument(name = "userId"){
                    nullable = true
                    defaultValue = null
                    type = NavType.StringType
                }
            )
        ) {
            val userId = it.arguments?.getString("userId")
            OtherUserScreen(navController = navController, userId = userId)
        }

        composable(
            route = Screen.NewPostScreen.route
        ) {
            NewPostScreen(navController = navController)
        }

        composable(
            route = Screen.NewsScreen.route
        ){
            NewsScreen(navController = navController)
        }

        composable(
            route = Screen.NewsDetailScreen.route + "?newsId={newsId}",
            arguments = listOf(
                navArgument(name = "newsId"){
                    nullable = true
                    defaultValue = null
                    type = NavType.StringType
                }
            )
        ) {
            val newsId = it.arguments?.getString("newsId")
            NewsDetailScreen(navController = navController, newsId = newsId)
        }
    }
}