package com.example.ecommerceshop.utils

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

class ThemeManager(context: Context) {

    private val pref =
        context.getSharedPreferences(
            "theme_pref",
            Context.MODE_PRIVATE
        )

    companion object {
        private const val DARK_MODE = "dark_mode"
    }

    fun isDarkMode(): Boolean {

        return pref.getBoolean(
            DARK_MODE,
            false
        )

    }

    fun setDarkMode(enable: Boolean) {

        pref.edit()
            .putBoolean(DARK_MODE, enable)
            .apply()

        if (enable) {

            AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_YES
            )

        } else {

            AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_NO
            )

        }

    }

}