package com.example.kolejka.view.util.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ExperimentalGraphicsApi
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.kolejka.view.ui.screens.login_screen.LoginScreen
import com.example.kolejka.view.ui.screens.password_change.change_password.ChangePasswordScreen
import com.example.kolejka.view.ui.screens.password_change.enter_email.EnterEmailScreen
import com.example.kolejka.view.ui.screens.password_change.enter_verification.EnterVerificationScreen
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

    NavHost(navController = mainNavController, startDestination = Screen.SplashScreen.route) {
        composable(route = Screen.SplashScreen.route) {
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

        composable(
            route = Screen.EnterEmailScreen.route
        ) {
            EnterEmailScreen(navController = mainNavController)
        }

        composable(
            route = Screen.EnterVerificationScreen.route + "?userId={userId}&code={code}",
            arguments = listOf(
                navArgument(name = "userId") {
                    nullable = true
                    defaultValue = null
                    type = NavType.StringType
                },
                navArgument(name = "code") {
                    nullable = true
                    defaultValue = null
                    type = NavType.StringType
                }
            )
        ) {
            val userId = it.arguments?.getString("userId")
            val code = it.arguments?.getString("code")
            EnterVerificationScreen(
                navController = mainNavController,
                userId = userId,
                code = code
            )
        }

        composable(
            route = Screen.ChangePasswordScreen.route + "?userId={userId}",
            arguments = listOf(
                navArgument(name = "userId") {
                    nullable = true
                    defaultValue = null
                    type = NavType.StringType
                }
            )
        ) {
            val userId = it.arguments?.getString("userId")
            ChangePasswordScreen(navController = mainNavController, userId = userId)
        }


        composable(route = Screen.AppHolderScreen.route) {
            val appNavController = rememberNavController()
            AppHolder(navController = appNavController, secondaryNavController = mainNavController)
        }
    }
}