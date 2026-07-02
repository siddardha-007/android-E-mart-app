package com.example.ecommerceshop.utils

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("ecommerce_pref", Context.MODE_PRIVATE)

    companion object {

        private const val IS_WALKTHROUGH_COMPLETED = "is_walkthrough_completed"

        private const val USER_NAME = "user_name"

        private const val USER_EMAIL = "user_email"

    }

    // Walkthrough

    fun setWalkthroughCompleted() {

        sharedPreferences.edit()
            .putBoolean(IS_WALKTHROUGH_COMPLETED, true)
            .apply()

    }

    fun logout() {

        sharedPreferences.edit()
            .remove(USER_NAME)
            .remove(USER_EMAIL)
            .apply()

    }

    fun isWalkthroughCompleted(): Boolean {

        return sharedPreferences.getBoolean(
            IS_WALKTHROUGH_COMPLETED,
            false
        )

    }

    // User Details (optional cache)

    fun saveUserDetails(
        name: String,
        email: String
    ) {

        sharedPreferences.edit()
            .putString(USER_NAME, name)
            .putString(USER_EMAIL, email)
            .apply()

    }

    fun getUserName(): String {

        return sharedPreferences.getString(
            USER_NAME,
            "Guest"
        ) ?: "Guest"

    }

    fun getUserEmail(): String {

        return sharedPreferences.getString(
            USER_EMAIL,
            ""
        ) ?: ""

    }

    fun clearUserData() {

        sharedPreferences.edit()
            .remove(USER_NAME)
            .remove(USER_EMAIL)
            .apply()

    }

}