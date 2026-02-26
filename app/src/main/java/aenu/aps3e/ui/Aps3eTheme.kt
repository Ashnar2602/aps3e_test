package aenu.aps3e.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class Aps3eColorPalette(
    val background: Color,
    val surface: Color,
    val primary: Color,
    val secondary: Color,
    val onBackground: Color,
    val onSurface: Color,
    val cardBackground: Color,
    val accent: Color,
    val warning: Color,
    val danger: Color
)

private val Aps3eDarkPalette = Aps3eColorPalette(
    background = Color(0xFF0A0E27),
    surface = Color(0xFF1A1F3A),
    primary = Color(0xFF4A90E2),
    secondary = Color(0xFF2BC4D9),
    onBackground = Color(0xFFFFFFFF),
    onSurface = Color(0xFFE0E0E0),
    cardBackground = Color(0xFF141829),
    accent = Color(0xFF6EE7F2),
    warning = Color(0xFFF2C94C),
    danger = Color(0xFFEB5757)
)

private val Aps3eLightPalette = Aps3eColorPalette(
    background = Color(0xFFEFF4FF),
    surface = Color(0xFFDCE7FF),
    primary = Color(0xFF3A77C8),
    secondary = Color(0xFF1F9DB0),
    onBackground = Color(0xFF121A2E),
    onSurface = Color(0xFF1A2540),
    cardBackground = Color(0xFFF8FAFF),
    accent = Color(0xFF0E7E9F),
    warning = Color(0xFFD79B21),
    danger = Color(0xFFD64545)
)

private val LocalAps3eColors = staticCompositionLocalOf { Aps3eDarkPalette }

object Aps3eColors {
    val Background: Color
        @Composable get() = LocalAps3eColors.current.background
    val Surface: Color
        @Composable get() = LocalAps3eColors.current.surface
    val Primary: Color
        @Composable get() = LocalAps3eColors.current.primary
    val Secondary: Color
        @Composable get() = LocalAps3eColors.current.secondary
    val OnBackground: Color
        @Composable get() = LocalAps3eColors.current.onBackground
    val OnSurface: Color
        @Composable get() = LocalAps3eColors.current.onSurface
    val CardBackground: Color
        @Composable get() = LocalAps3eColors.current.cardBackground
    val Accent: Color
        @Composable get() = LocalAps3eColors.current.accent
    val Warning: Color
        @Composable get() = LocalAps3eColors.current.warning
    val Danger: Color
        @Composable get() = LocalAps3eColors.current.danger
}

@Composable
fun Aps3eTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val palette = if (darkTheme) Aps3eDarkPalette else Aps3eLightPalette
    val colorScheme = if (darkTheme) {
        darkColorScheme(
            background = palette.background,
            surface = palette.surface,
            primary = palette.primary,
            secondary = palette.secondary,
            onPrimary = Color.White,
            onBackground = palette.onBackground,
            onSurface = palette.onSurface
        )
    } else {
        lightColorScheme(
            background = palette.background,
            surface = palette.surface,
            primary = palette.primary,
            secondary = palette.secondary,
            onPrimary = Color.White,
            onBackground = palette.onBackground,
            onSurface = palette.onSurface
        )
    }

    CompositionLocalProvider(LocalAps3eColors provides palette) {
        MaterialTheme(
            colorScheme = colorScheme,
            content = content
        )
    }
}
