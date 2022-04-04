package com.example.kolejka.view.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorPalette = lightColors(
    primary = DarkPurple,
    background = LightBackgroundWhite,
    primaryVariant = MediumOpaquePurple,
    secondary = DarkGray,
    onBackground = PostWhite,
    secondaryVariant = DarkGray,
    onSurface = BlackAccent,
)

private val DarkColorPalette = darkColors(
    primary = DarkPurple,
    background = DarkBackgroundGray,
    primaryVariant = LightOpaquePurple,
    secondary = LightGray,
    secondaryVariant = Color.White,
    onBackground = PostDark,
    onSurface = BlackAccent
)

@Composable
fun KolejkaTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {

    val colors = if(darkTheme) DarkColorPalette else LightColorPalette

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}