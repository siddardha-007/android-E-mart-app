package com.example.ecommerceshop.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ecommerceshop.databinding.FragmentProfileBinding
import com.example.ecommerceshop.ui.emailauth.EmailLoginActivity
import com.example.ecommerceshop.utils.PreferenceManager
import com.example.ecommerceshop.utils.ThemeManager
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var themeManager: ThemeManager
    private lateinit var preferenceManager: PreferenceManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        themeManager = ThemeManager(requireContext())
        preferenceManager = PreferenceManager(requireContext())

        // 1. Set the visual state FIRST without triggering any listeners
        binding.switchDarkMode.isChecked = themeManager.isDarkMode()

        // 2. Attach the listener AFTER setting the checked state
        binding.switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked != themeManager.isDarkMode()) {
                themeManager.setDarkMode(isChecked)
                requireActivity().recreate()
            }
        }

        loadUserData()
        setupClickListeners()
    }

    private fun loadUserData() {
        val user = FirebaseAuth.getInstance().currentUser ?: return

        com.google.firebase.firestore.FirebaseFirestore.getInstance()
            .collection("users")
            .document(user.uid)
            .get()
            .addOnSuccessListener { document ->
                // FIX: Safely access binding only if the view hasn't been destroyed
                _binding?.let { currentBinding ->
                    val name = document.getString("name") ?: "Guest"
                    currentBinding.tvName.text = "Hello, $name"
                }
            }
            .addOnFailureListener {
                // FIX: Safely access binding here as well
                _binding?.let { currentBinding ->
                    currentBinding.tvName.text = "Hello, Guest"
                }
            }
    }

    private fun setupClickListeners() {
        binding.cardOrders.setOnClickListener {
            // TODO Open Orders Screen
        }

        binding.cardAddress.setOnClickListener {
            // TODO Open Address Screen
        }

        binding.cardCart.setOnClickListener {
            // TODO Open Cart Fragment
        }

        binding.btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            preferenceManager.logout()

            val intent = Intent(requireContext(), EmailLoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            requireActivity().finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}