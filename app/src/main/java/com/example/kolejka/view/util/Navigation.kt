package com.example.kolejka.view.util

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ExperimentalGraphicsApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.kolejka.models.Post
import com.example.kolejka.models.User
import com.example.kolejka.view.ui.screens.edit_profile_dialog.EditProfileDialog
import com.example.kolejka.view.ui.screens.login_screen.LoginScreen
import com.example.kolejka.view.ui.screens.main_post_screen.PostScreen
import com.example.kolejka.view.ui.screens.new_post_screen.NewPostScreen
import com.example.kolejka.view.ui.screens.notification_screen.NotificationScreen
import com.example.kolejka.view.ui.screens.post_detail_screen.PostDetailScreen
import com.example.kolejka.view.ui.screens.profile_screen.ProfileScreen
import com.example.kolejka.view.ui.screens.register_screen.RegisterScreen
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalGraphicsApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
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
            ProfileScreen(navController = navController, User(userId = "sfdfad", profilePictureUrl = "", username = "Petr Horáček"))
        }

        composable(
                route = Screen.PostDetailScreen.route
        ) {
            PostDetailScreen(
                    navController = navController,
                    post = Post(
                            title = "Hamehamehafa",
                            username = "Petr Horáček",
                            description = "dopkasd d apfd dsfpokd fpsf apsdofk pasokf asdpofkasd ffpasdof fodkfod fapodkfas dfpsodf apdod fodf apsdofak spokfds apfo podskfoasdkfpokasd fsadp kasd paspdkapsd aspdaoskdpfhgfhgfhfghfg haksp dkaosdk poka sdpkspa aka fuka fundi luka luka fuka motorku dsheoioj ",
                            limit = 10,
                            members = listOf("Petr Horáček")
                    )
            )
        }

        composable(
            route = Screen.NewPostScreen.route
        ){
            NewPostScreen(navController = navController)
        }
    }
}