package com.example.ecommerce.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ecommerce.databinding.FragmentProfileBinding
import com.example.ecommerce.ui.auth.LoginActivity
import com.example.ecommerce.utils.PreferenceManager

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var preferenceManager: PreferenceManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        preferenceManager = PreferenceManager(requireContext())

        loadUserData()

        setupClickListeners()

        return binding.root
    }

    private fun loadUserData() {

        binding.tvName.text =
            preferenceManager.getUserName()

        binding.tvPhone.text =
            preferenceManager.getPhoneNumber()

    }

    private fun setupClickListeners() {

        binding.cardOrders.setOnClickListener {

            // TODO Open Orders Screen

        }

        binding.cardAddress.setOnClickListener {

            // TODO Open Address Screen

        }

        //       binding.cardSettings.setOnClickListener {
//
//            // TODO Open Settings Screen
//
//        }

        binding.cardCart.setOnClickListener {

            // TODO Open Cart Fragment

        }

        binding.btnLogout.setOnClickListener {

            preferenceManager.logout()

            startActivity(
                Intent(
                    requireContext(),
                    LoginActivity::class.java
                )
            )

            requireActivity().finish()

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

}