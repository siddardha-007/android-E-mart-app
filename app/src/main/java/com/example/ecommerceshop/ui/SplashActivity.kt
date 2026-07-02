package com.example.ecommerceshop.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.example.ecommerceshop.databinding.ActivitySplashBinding
import com.example.ecommerceshop.ui.emailauth.EmailLoginActivity
import com.example.ecommerceshop.ui.main.MainActivity
import com.example.ecommerceshop.utils.PreferenceManager
import com.example.ecommerceshop.utils.ThemeManager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Apply saved theme before showing any UI
        val themeManager = ThemeManager(this)

        AppCompatDelegate.setDefaultNightMode(
            if (themeManager.isDarkMode()) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {

            delay(2000)

            val preferenceManager = PreferenceManager(this@SplashActivity)

            val intent = when {

                !preferenceManager.isWalkthroughCompleted() -> {

                    Intent(
                        this@SplashActivity,
                        IntroActivity::class.java
                    )

                }

                FirebaseAuth.getInstance().currentUser != null -> {

                    Intent(
                        this@SplashActivity,
                        MainActivity::class.java
                    )

                }

                else -> {

                    Intent(
                        this@SplashActivity,
                        EmailLoginActivity::class.java
                    )

                }

            }

            startActivity(intent)
            finish()

        }

    }

}