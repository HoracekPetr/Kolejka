package com.example.kolejka.view.ui.components.bottom_navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.material.BadgedBox
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.kolejka.KolejkaApp
import com.example.kolejka.R
import com.example.kolejka.view.theme.*
import com.example.kolejka.view.util.Screen

@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>,
    navController: NavController,
    showBottomBar: Boolean,
    modifier: Modifier = Modifier,
    onItemClick: (BottomNavItem) -> Unit,
    viewModel: BottomNavBarViewModel = hiltViewModel()
) {
    val backStackEntry = navController.currentBackStackEntryAsState()


    if (showBottomBar) {
        BottomNavigation(
            modifier = modifier,
            backgroundColor = LightGray,
            elevation = Space8
        ) {
            items.forEach { item ->
                val selected = item.route == backStackEntry.value?.destination?.route
                BottomNavigationItem(
                    selected = selected,
                    selectedContentColor = DarkPurple,
                    unselectedContentColor = DarkGray,
                    onClick = {
                        onItemClick(item)
                        KolejkaApp.notificationsCount = 0
                    },
                    icon = {
                        if (item.notificationCount != 0) {
                            BadgedBox(badge = {
                                Text(
                                    text = item.notificationCount.toString(),
                                    style = MaterialTheme.typography.caption,
                                    color = DarkPurple,
                                )
                            }) {
                                Column(horizontalAlignment = CenterHorizontally) {
                                    Icon(imageVector = item.icon, contentDescription = item.name)
                                    if (selected) {
                                        Text(
                                            text = item.name,
                                            style = Typography.caption,
                                            color = DarkPurple
                                        )
                                    }
                                }
                            }
                        } else {
                            Column(horizontalAlignment = CenterHorizontally) {
                                Icon(imageVector = item.icon, contentDescription = item.name)
                                if (selected) {
                                    Text(
                                        text = item.name,
                                        style = Typography.caption,
                                        color = DarkPurple
                                    )
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}
