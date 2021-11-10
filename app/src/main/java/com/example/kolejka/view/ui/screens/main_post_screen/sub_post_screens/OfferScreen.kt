package com.example.kolejka.view.ui.screens.main_post_screen.sub_post_screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.kolejka.view.ui.components.posts.Post
import com.example.kolejka.view.ui.components.posts.PostList

val offerList = listOf(
    Post("Guláš", "HAMEHAMEHAFASABESABESLAFAFASDASPDOKASPDOKASDPOPOKPKPASDDPASD"),
    Post("Salát", "HAMEHAMEHAFASABESABESLAFAFASDASPDOKASPDOKASDPOPOKPKPASDDPASD"),
    Post("Staré židle", "HAMEHAMEHAFASABESABESLAFAFASDASPDOKASPDOKASDPOPOKPKPASDDPASD"),
    Post("Volant od felicie", "HAMEHAMEHAFASABESABESLAFAFASDASPDOKASPDOKASDPOPOKPKPASDDPASD"),
    Post("Humahumahuma", "HAMEHAMEHAFASABESABESLAFAFASDASPDOKASPDOKASDPOPOKPKPASDDPASD")
)

@Composable
fun OfferScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        PostList(offerList)
    }
}