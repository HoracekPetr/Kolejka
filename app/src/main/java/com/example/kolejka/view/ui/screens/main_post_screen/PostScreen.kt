package com.example.kolejka.view.ui.screens.main_post_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.kolejka.view.ui.components.posts.Post
import com.example.kolejka.view.ui.components.posts.PostComposable
import com.example.kolejka.view.ui.components.posts.PostList


val postList = listOf(
    Post("Fotbal na Harcově", "HAMEHAMEHAFASABESABESLAFAFASDASPDOKASPDOKASDPOPOKPKPASDDPASD"),
    Post("Gungoslav", "HAMEHAMEHAFASABESABESLAFAFASDASPDOKASPDOKASDPOPOKPKPASDDPASD"),
    Post("Pěkně smradlavá prdel kurva", "HAMEHAMEHAFASABESABESLAFAFASDASPDOKASPDOKASDPOPOKPKPASDDPASD"),
    Post("Shit Party", "HAMEHAMEHAFASABESABESLAFAFASDASPDOKASPDOKASDPOPOKPKPASDDPASD"),
    Post("BEEEERPONKKK", "HAMEHAMEHAFASABESABESLAFAFASDASPDOKASPDOKASDPOPOKPKPASDDPASD")
)

@Composable
fun PostScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()){
        PostList(posts = postList)
    }
}