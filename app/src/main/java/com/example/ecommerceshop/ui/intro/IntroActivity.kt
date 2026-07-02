package com.example.ecommerceshop.ui.intro

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ecommerceshop.databinding.ActivityIntroBinding
import com.example.ecommerceshop.ui.emailauth.EmailLoginActivity
import com.example.ecommerceshop.utils.PreferenceManager

class IntroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIntroBinding
    private lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        preferenceManager = PreferenceManager(this)

        binding.btnNext.setOnClickListener {

            preferenceManager.setWalkthroughCompleted()

            startActivity(
                Intent(this, EmailLoginActivity::class.java)
            )

            finish()

        }

        binding.tvSkip.setOnClickListener {

            preferenceManager.setWalkthroughCompleted()

            startActivity(
                Intent(this, EmailLoginActivity::class.java)
            )

            finish()

        }

    }
}