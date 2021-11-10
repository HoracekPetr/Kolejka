package com.example.kolejka.view.ui.screens.main_post_screen.sub_post_screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.kolejka.view.ui.components.posts.Post
import com.example.kolejka.view.ui.components.posts.PostList

val eventList = listOf(
    Post("Fotbal na Harcově", "HAMEHAMEHAFASABESABESLAFAFASDASPDOKASPDOKASDPOPOKPKPASDDPASD"),
    Post("Gungoslav", "HAMEHAMEHAFASABESABESLAFAFASDASPDOKASPDOKASDPOPOKPKPASDDPASD"),
    Post("Pěkně smradlavá prdel kurva", "HAMEHAMEHAFASABESABESLAFAFASDASPDOKASPDOKASDPOPOKPKPASDDPASD"),
    Post("Shit Party", "HAMEHAMEHAFASABESABESLAFAFASDASPDOKASPDOKASDPOPOKPKPASDDPASD"),
    Post("BEEEERPONKKK", "HAMEHAMEHAFASABESABESLAFAFASDASPDOKASPDOKASDPOPOKPKPASDDPASD")
)

@Composable
fun EventScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        PostList(eventList)
    }
}