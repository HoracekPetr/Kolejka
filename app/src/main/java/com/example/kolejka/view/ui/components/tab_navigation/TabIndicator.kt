package com.example.kolejka.view.ui.components.tab_navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TabPosition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.kolejka.view.theme.LightPurple
import com.example.kolejka.view.theme.Space2
import com.example.kolejka.view.theme.Space4
import com.example.kolejka.view.theme.Space8

@Composable
fun TabIndicator(tabPosition: List<TabPosition>, index: Int) {
    val width = tabPosition[index].width
    val left = tabPosition[index].left
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(align = Alignment.BottomStart)
            .offset(x = left)
            .width(width)
            .padding(Space4)
            .fillMaxSize()
            .clip(RoundedCornerShape(Space4))
            .border(2.dp, LightPurple)
    )
}