package com.example.kolejka.view.ui.components.tab_navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.ui.graphics.vector.ImageVector

enum class TabItem(val icon: ImageVector, val title: String) {
    Event(Icons.Default.Event, "Event"),
    Offer(Icons.Default.LocalOffer, "Offer")
}