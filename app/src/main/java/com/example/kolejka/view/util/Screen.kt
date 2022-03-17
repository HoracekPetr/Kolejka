package com.example.kolejka.view.util

sealed class Screen(val route: String) {
    object SplashScreen: Screen("splash_screen")
    object LoginScreen: Screen("login_screen")
    object RegisterScreen: Screen("register_screen")

    object AppHolderScreen: Screen("app_holder_screen")

    object PostScreen: Screen("post_screen")
    object NotificationScreen: Screen("notification_screen")
    object ProfileScreen: Screen("profile_screen")
    object PostDetailScreen: Screen("post_detail_screen")
    object OtherUserScreen: Screen("other_user_screen")
    object NewPostScreen: Screen("new_post_screen")
}