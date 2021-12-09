package com.example.kolejka.view.ui.screens.main_post_screen.sub_post_screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.kolejka.models.Post
import com.example.kolejka.view.ui.components.posts.PostList

val eventList = listOf(
    Post("Fotbal na Harcově", description = "HAMEHAMEHAFASABESABESLAFAFASDASPDOKASPDOKASDPOPOKPKPASDDPASD", type = 0),
    Post("Gungoslav", description = "HAMEHAMEHAFASABESABESLAFAFASDASPDOKASPDOKASDPOPOKPKPASDDPASD"),
    Post("Pěkně smradlavá prdel kurva", description = "HAMEHAMEHAFASABESABESLAFAFASDASPDOKASPDOKASDPOPOKPKPASDDPASD"),
    Post("Shit Party", description = "HAMEHAMEHAFASABESABESLAFAFASDASPDOKASPDOKASDPOPOKPKPASDDPASD"),
    Post("BEEEERPONKKK", description = "HAMEHAMEHAFASABESABESLAFAFASDASPDOKASPDOKASDPOPOKPKPASDDPASD")
)

@Composable
fun EventScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        PostList(eventList, navController)
    }
}