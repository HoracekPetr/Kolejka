package com.example.kolejka.view.util

sealed class Screen(val route: String) {
    //AUTH
    object SplashScreen : Screen("splash_screen")
    object LoginScreen : Screen("login_screen")
    object RegisterScreen : Screen("register_screen")
    object EnterEmailScreen: Screen("enter_email_screen")
    object EnterVerificationScreen: Screen("enter_verification_screen")
    object ChangePasswordScreen: Screen("change_password_screen")

    //APP
    object AppHolderScreen: Screen("app_holder_screen")

    object PostScreen: Screen("post_screen")
    object NotificationScreen: Screen("notification_screen")
    object ProfileScreen: Screen("profile_screen")
    object PostDetailScreen: Screen("post_detail_screen")
    object OtherUserScreen: Screen("other_user_screen")
    object NewPostScreen: Screen("new_post_screen")
    object EditPostScreen: Screen("edit_post_screen")
}