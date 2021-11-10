package com.example.kolejka.view.ui.screens.main_post_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.kolejka.view.ui.components.posts.Post
import com.example.kolejka.view.ui.components.posts.PostComposable
import com.example.kolejka.view.ui.components.posts.PostList
import com.example.kolejka.view.ui.components.tab_navigation.TabItem
import com.example.kolejka.view.ui.components.tab_navigation.Tabs
import com.example.kolejka.view.ui.screens.main_post_screen.sub_post_screens.EventScreen
import com.example.kolejka.view.ui.screens.main_post_screen.sub_post_screens.OfferScreen
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch


@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun PostScreen(navController: NavController) {

    val pagerState = rememberPagerState(pageCount = TabItem.values().size)
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            Tabs(selectedTabIndex = pagerState.currentPage, onSelectedTab = {scope.launch{
                pagerState.animateScrollToPage(it.ordinal)
            }})
        }
    ) {
        HorizontalPager(state = pagerState) { index ->
            when(index){
                0 -> EventScreen()
                1 -> OfferScreen()
            }
        }
/*        when(tabPage){
            TabItem.Offer -> OfferScreen()
            TabItem.Event -> EventScreen()
        }*/
    }
}