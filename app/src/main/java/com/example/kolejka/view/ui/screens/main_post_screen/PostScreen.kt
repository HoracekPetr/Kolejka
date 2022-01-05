package com.example.kolejka.view.ui.screens.main_post_screen

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
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
                0 -> EventScreen(navController)
                1 -> OfferScreen(navController)
            }
        }
/*        when(tabPage){
            TabItem.Offer -> OfferScreen()
            TabItem.Event -> EventScreen()
        }*/
    }
}