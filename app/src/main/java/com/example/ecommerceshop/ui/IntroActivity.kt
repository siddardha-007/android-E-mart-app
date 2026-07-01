package com.example.ecommerceshop.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ecommerceshop.databinding.ActivityIntroBinding
import com.example.ecommerceshop.ui.auth.LoginActivity
import com.example.ecommerceshop.ui.main.MainActivity
import com.example.ecommerceshop.utils.SessionManager

class IntroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val sessionManager = SessionManager(this)

        // 1. Primary "Shop Now" Button
        binding.btnNext.setOnClickListener {
            sessionManager.isIntroCompleted = true
            navigateToLogin()
        }

        // 2. Top-Right "Skip" Text Link -> Bypasses to Home screen for Testing
        binding.tvSkip.setOnClickListener {
            sessionManager.isIntroCompleted = true

            val intent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
            finish()
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}