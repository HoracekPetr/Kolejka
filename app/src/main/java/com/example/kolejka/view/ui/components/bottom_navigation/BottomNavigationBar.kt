package com.example.kolejka.view.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.kolejka.view.theme.*
import com.example.kolejka.view.ui.components.bottom_navigation.BottomNavItem

@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>,
    navController: NavController,
    showBottomBar: Boolean,
    modifier: Modifier = Modifier,
    onItemClick: (BottomNavItem) -> Unit
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
                        item.notificationCount = 0
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
