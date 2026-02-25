package aenu.aps3e.ui

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color
import androidx.preference.PreferenceManager

private const val PREF_THEME_MODE = "ui_theme_mode"

enum class ThemeMode(val value: String) {
    SYSTEM("system"),
    LIGHT("light"),
    DARK("dark");

    companion object {
        @JvmStatic
        fun fromStored(value: String?): ThemeMode {
            return entries.firstOrNull { it.value == value } ?: SYSTEM
        }
    }
}

object ThemePreferences {
    @JvmStatic
    fun getThemeMode(context: Context): ThemeMode {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        return ThemeMode.fromStored(prefs.getString(PREF_THEME_MODE, ThemeMode.SYSTEM.value))
    }

    @JvmStatic
    fun setThemeMode(context: Context, mode: ThemeMode) {
        PreferenceManager.getDefaultSharedPreferences(context)
            .edit()
            .putString(PREF_THEME_MODE, mode.value)
            .apply()
    }
}

object Aps3eColors {
    var Background = Color(0xFF0A0E27)
    var Surface = Color(0xFF1A1F3A)
    var Primary = Color(0xFF4A90E2)
    var Secondary = Color(0xFF2BC4D9)
    var OnBackground = Color(0xFFFFFFFF)
    var OnSurface = Color(0xFFE0E0E0)
    var CardBackground = Color(0xFF141829)
    var Accent = Color(0xFF6EE7F2)
    var Warning = Color(0xFFF2C94C)
    var Danger = Color(0xFFEB5757)

    fun applyPalette(isDark: Boolean) {
        if (isDark) {
            Background = Color(0xFF0A0E27)
            Surface = Color(0xFF1A1F3A)
            Primary = Color(0xFF4A90E2)
            Secondary = Color(0xFF2BC4D9)
            OnBackground = Color(0xFFFFFFFF)
            OnSurface = Color(0xFFE0E0E0)
            CardBackground = Color(0xFF141829)
            Accent = Color(0xFF6EE7F2)
            Warning = Color(0xFFF2C94C)
            Danger = Color(0xFFEB5757)
        } else {
            Background = Color(0xFFEAF1FF)
            Surface = Color(0xFFF4F8FF)
            Primary = Color(0xFF2E6BB8)
            Secondary = Color(0xFF1B8FA1)
            OnBackground = Color(0xFF101A33)
            OnSurface = Color(0xFF1D2438)
            CardBackground = Color(0xFFDCE8FF)
            Accent = Color(0xFF2F9CCF)
            Warning = Color(0xFFD79A1B)
            Danger = Color(0xFFC53B3B)
        }
    }
}

@Composable
fun Aps3eTheme(content: @Composable () -> Unit) {
    val context = LocalContext.current
    val systemDark = isSystemInDarkTheme()
    val prefs = remember(context) { PreferenceManager.getDefaultSharedPreferences(context) }
    var themeMode by remember(context) { mutableStateOf(ThemePreferences.getThemeMode(context)) }

    DisposableEffect(prefs) {
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            if (key == PREF_THEME_MODE) {
                themeMode = ThemePreferences.getThemeMode(context)
            }
        }
        prefs.registerOnSharedPreferenceChangeListener(listener)
        onDispose {
            prefs.unregisterOnSharedPreferenceChangeListener(listener)
        }
    }

    val useDarkTheme = when (themeMode) {
        ThemeMode.SYSTEM -> systemDark
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
    }

    Aps3eColors.applyPalette(useDarkTheme)

    val colorScheme = if (useDarkTheme) {
        darkColorScheme(
            background = Aps3eColors.Background,
            surface = Aps3eColors.Surface,
            primary = Aps3eColors.Primary,
            secondary = Aps3eColors.Secondary,
            onBackground = Aps3eColors.OnBackground,
            onSurface = Aps3eColors.OnSurface
        )
    } else {
        lightColorScheme(
            background = Color(0xFFEAF1FF),
            surface = Color(0xFFF4F8FF),
            primary = Color(0xFF2E6BB8),
            secondary = Color(0xFF1B8FA1),
            onBackground = Color(0xFF101A33),
            onSurface = Color(0xFF1D2438)
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
