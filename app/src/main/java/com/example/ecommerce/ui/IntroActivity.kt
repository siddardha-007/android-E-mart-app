package com.example.ecommerce.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ecommerce.R
import com.example.ecommerce.databinding.ActivityIntroBinding
import com.example.ecommerce.ui.auth.LoginActivity
import com.example.ecommerce.ui.main.MainActivity
import com.example.ecommerce.utils.SessionManager

class IntroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIntroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sessionManager = SessionManager(this)

        // 1. Primary "Get Started" Button
        binding.btnNext.setOnClickListener {
            // Mark walkthrough as completed forever
            sessionManager.isIntroCompleted = true
            navigateToLogin()
        }

        // 2. "Log In" Inline Text Link
        binding.tvGoToLogin.setOnClickListener {
            // Mark walkthrough as completed forever
            sessionManager.isIntroCompleted = true
            navigateToLogin()
        }

        // 3. Top-Right "Skip" Text Link
        binding.tvSkip.setOnClickListener {
            // Mark walkthrough as completed forever
            sessionManager.isIntroCompleted = true
            //TESTING
            startActivity(
                Intent(this, MainActivity::class.java)
            )
//            navigateToLogin()
        }
    }
    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}