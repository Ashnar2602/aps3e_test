package aenu.aps3e

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager

object AppThemeManager {
    private const val PREF_KEY_THEME_MODE = "app_theme_mode"

    enum class ThemeMode(val prefValue: String, val nightMode: Int) {
        SYSTEM("system", AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM),
        LIGHT("light", AppCompatDelegate.MODE_NIGHT_NO),
        DARK("dark", AppCompatDelegate.MODE_NIGHT_YES);

        companion object {
            fun fromPrefValue(value: String?): ThemeMode {
                return values().firstOrNull { it.prefValue == value } ?: SYSTEM
            }
        }
    }

    @JvmStatic
    fun getThemeMode(context: Context): ThemeMode {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val value = prefs.getString(PREF_KEY_THEME_MODE, ThemeMode.SYSTEM.prefValue)
        return ThemeMode.fromPrefValue(value)
    }

    @JvmStatic
    fun setThemeMode(context: Context, mode: ThemeMode) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        prefs.edit().putString(PREF_KEY_THEME_MODE, mode.prefValue).apply()
        AppCompatDelegate.setDefaultNightMode(mode.nightMode)
    }

    @JvmStatic
    fun applySavedTheme(context: Context) {
        AppCompatDelegate.setDefaultNightMode(getThemeMode(context).nightMode)
    }
}
