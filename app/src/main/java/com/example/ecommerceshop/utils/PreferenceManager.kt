package com.example.ecommerceshop.utils

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("ecommerce_pref", Context.MODE_PRIVATE)

    companion object {

        private const val IS_LOGGED_IN = "is_logged_in"
        private const val USER_NAME = "user_name"
        private const val PHONE_NUMBER = "phone_number"

    }

    /**
     * Save Login Session
     */
    fun saveLoginSession(
        name: String,
        phone: String
    ) {

        sharedPreferences.edit()
            .putBoolean(IS_LOGGED_IN, true)
            .putString(USER_NAME, name)
            .putString(PHONE_NUMBER, phone)
            .apply()

    }

    /**
     * Check Login
     */
    fun isLoggedIn(): Boolean {

        return sharedPreferences.getBoolean(
            IS_LOGGED_IN,
            false
        )

    }

    /**
     * Username
     */
    fun getUserName(): String {

        return sharedPreferences.getString(
            USER_NAME,
            "Guest User"
        ) ?: "Guest User"

    }

    /**
     * Phone Number
     */
    fun getPhoneNumber(): String {

        return sharedPreferences.getString(
            PHONE_NUMBER,
            ""
        ) ?: ""

    }

    /**
     * Logout
     */
    fun logout() {

        sharedPreferences.edit().clear().apply()

    }

}