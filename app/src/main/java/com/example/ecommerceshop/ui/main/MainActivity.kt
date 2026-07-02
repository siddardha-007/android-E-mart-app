package com.example.ecommerceshop.ui.main

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import com.example.ecommerceshop.R
import com.example.ecommerceshop.databinding.ActivityMainBinding
import com.example.ecommerceshop.ui.fragments.CartFragment
import com.example.ecommerceshop.ui.fragments.HomeFragment
import com.example.ecommerceshop.ui.fragments.ProductsFragment
import com.example.ecommerceshop.ui.fragments.ProfileFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.itemIconTintList = null

        // FIX: Dynamically separate the fragment container from the bottom navigation
        // It calculates the height of the bar + its margins so fragments never overlap.
        binding.bottomNavigationContainer.post {
            val navBarHeight = binding.bottomNavigationContainer.height
            val layoutParams = binding.bottomNavigationContainer.layoutParams as ViewGroup.MarginLayoutParams
            val totalBarOffset = navBarHeight + layoutParams.bottomMargin

            // Constrain the fragment container to end right where the navigation pill begins
            binding.fragmentContainer.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                bottomMargin = totalBarOffset
            }
        }

        // Setup Selection Listener
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.nav_products -> {
                    replaceFragment(ProductsFragment())
                    true
                }
                R.id.nav_cart -> {
                    replaceFragment(CartFragment())
                    true
                }
                R.id.nav_profile -> {
                    replaceFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }

        // Initialize Default Screen cleanly without double-loading
        if (savedInstanceState == null) {
            binding.bottomNavigation.selectedItemId = R.id.nav_home

            // Creates the red notification badge over your cart icon
            val badge = binding.bottomNavigation.getOrCreateBadge(R.id.nav_cart)
            badge.isVisible = true
            badge.number = 4
            badge.backgroundColor = Color.parseColor("#FF3B30")
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}