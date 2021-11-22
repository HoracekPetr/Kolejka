package com.example.kolejka.view.util

sealed class Screen(val route: String) {
    object LoginScreen: Screen("login_screen")
    object RegisterScreen: Screen("register_screen")
    object PostScreen: Screen("post_screen")
    object NotificationScreen: Screen("notification_screen")
    object ProfileScreen: Screen("profile_screen")
    object PostDetailScreen: Screen("post_detail_screen")
    object NewPostScreen: Screen("new_post_screen")
}