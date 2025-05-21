package com.example.gamescoreapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// PALETTE PERSONNALISÉE

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF673AB7),       // Violet
    onPrimary = Color.White,
    secondary = Color(0xFF03A9F4),     // Bleu clair
    onSecondary = Color.Black,
    background = Color(0xFFF4F4F4),
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF9575CD),       // Violet clair
    onPrimary = Color.Black,
    secondary = Color(0xFF81D4FA),     // Bleu doux
    onSecondary = Color.Black,
    background = Color(0xFF121212),
    onBackground = Color.White,
    surface = Color(0xFF1E1E1E),
    onSurface = Color.White
)

// THÈME DE L'APP

@Composable
fun GameScoreAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(),
        content = content
    )
}
