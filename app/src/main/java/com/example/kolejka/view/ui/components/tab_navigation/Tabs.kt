package com.example.kolejka.view.ui.components.tab_navigation

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.kolejka.view.theme.DarkGray
import com.example.kolejka.view.theme.DarkPurple
import com.example.kolejka.view.theme.LightGray
import com.example.kolejka.view.theme.LightPurple
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun Tabs(selectedTabIndex: Int, onSelectedTab: (TabItem) -> Unit) {
    TabRow(
        selectedTabIndex = selectedTabIndex,
        backgroundColor = DarkPurple,
        indicator = {
            TabIndicator(
                tabPosition = it,
                index = selectedTabIndex
            )
        },
    ) {
        TabItem.values().forEachIndexed { index, tabItem ->
            LeadingIconTab(
                selected = index == selectedTabIndex,
                onClick = { onSelectedTab(tabItem) },
                text = { Text(text = tabItem.title) },
                icon = { Icon(imageVector = tabItem.icon, contentDescription = null) },
                unselectedContentColor = LightGray,
                selectedContentColor = Color.White
            )
        }
    }
}