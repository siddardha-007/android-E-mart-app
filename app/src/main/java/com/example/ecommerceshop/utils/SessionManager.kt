package com.example.ecommerceshop.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        PREF_NAME,
        Context.MODE_PRIVATE
    )
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    companion object {
        private const val PREF_NAME = "ecommerce_user_session"

        // Keys
        private const val KEY_IS_INTRO_COMPLETED = "is_intro_completed"
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_USER_NAME = "user_name"
        private const val KEY_PHONE_NUMBER = "phone_number"
    }

    // --- Intro Walkthrough Flags ---
    var isIntroCompleted: Boolean
        get() = sharedPreferences.getBoolean(KEY_IS_INTRO_COMPLETED, false)
        set(value) {
            editor.putBoolean(KEY_IS_INTRO_COMPLETED, value)
            editor.apply()
        }

    // --- User Session Profile Management ---
    var isLoggedIn: Boolean
        get() = sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
        set(value) {
            editor.putBoolean(KEY_IS_LOGGED_IN, value)
            editor.apply()
        }

    var userId: String?
        get() = sharedPreferences.getString(KEY_USER_ID, null)
        set(value) {
            editor.putString(KEY_USER_ID, value)
            editor.apply()
        }

    var userName: String?
        get() = sharedPreferences.getString(KEY_USER_NAME, null)
        set(value) {
            editor.putString(KEY_USER_NAME, value)
            editor.apply()
        }

    var phoneNumber: String?
        get() = sharedPreferences.getString(KEY_PHONE_NUMBER, null)
        set(value) {
            editor.putString(KEY_PHONE_NUMBER, value)
            editor.apply()
        }

    // --- Clear Session Logic ---
    fun clearSession() {
        // Keeps intro complete flag so returning users don't see onboarding again
        val introState = isIntroCompleted

        editor.clear()
        editor.apply()

        isIntroCompleted = introState
    }
}