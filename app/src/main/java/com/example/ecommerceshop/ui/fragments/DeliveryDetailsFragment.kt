package com.example.ecommerceshop.ui.cart

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.ecommerceshop.databinding.FragmentDeliveryDetailsBinding
import com.example.ecommerceshop.ui.payment.PaymentSuccessActivity
import com.example.ecommerceshop.viewmodel.CartViewModel
import com.google.android.material.button.MaterialButton

class DeliveryDetailsFragment : Fragment() {

    private var _binding: FragmentDeliveryDetailsBinding? = null
    private val binding get() = _binding!!

    // Uses your shared cart session view model
    private val cartViewModel: CartViewModel by activityViewModels()

    // Selection Tracking Variables
    private var selectedAddressType = "Office" // Default selection
    private var selectedPaymentMode = "Cash"   // Default selection

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDeliveryDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeCartTotal()
        setupSelectionListeners()

        binding.btnCheckout.setOnClickListener {
            val name = binding.etCustomerName.text.toString().trim()
            val mobile = binding.etCustomerMobile.text.toString().trim()
            val address = binding.etCustomerAddress.text.toString().trim()

            if (name.isEmpty() || mobile.isEmpty() || address.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill out all information fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 1. CLEAR ROOM DATABASE IMMEDIATELY
            // This prevents old items from showing up again when the user re-opens the cart later
            cartViewModel.clearCart()

            // 2. OPEN THE SUCCESS SCREEN
            val intent = Intent(requireContext(), PaymentSuccessActivity::class.java).apply {
                putExtra("CUSTOMER_NAME", name)
                putExtra("ADDRESS_TYPE", selectedAddressType)
                putExtra("PAYMENT_MODE", selectedPaymentMode)
            }
            startActivity(intent)
        }
    }

    private fun setupSelectionListeners() {
        // Address Selection Group
        binding.btnTypeOffice.setOnClickListener {
            selectedAddressType = "Office"
            updateButtonSelectionState(binding.btnTypeOffice, true)
            updateButtonSelectionState(binding.btnTypeHome, false)
        }

        binding.btnTypeHome.setOnClickListener {
            selectedAddressType = "Home"
            updateButtonSelectionState(binding.btnTypeHome, true)
            updateButtonSelectionState(binding.btnTypeOffice, false)
        }

        // Payment Mode Selection Group
        binding.btnPaymentCash.setOnClickListener {
            selectedPaymentMode = "Cash"
            updateButtonSelectionState(binding.btnPaymentCash, true)
            updateButtonSelectionState(binding.btnPaymentUpi, false)
        }

        binding.btnPaymentUpi.setOnClickListener {
            selectedPaymentMode = "UPI"
            updateButtonSelectionState(binding.btnPaymentUpi, true)
            updateButtonSelectionState(binding.btnPaymentCash, false)
        }
    }

    private fun updateButtonSelectionState(button: MaterialButton, isSelected: Boolean) {
        if (isSelected) {
            button.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#2A83A2"))
            button.iconTint = ColorStateList.valueOf(Color.WHITE)
            button.setTextColor(Color.WHITE)
            button.strokeWidth = 0
        } else {
            button.backgroundTintList = ColorStateList.valueOf(Color.WHITE)
            button.iconTint = ColorStateList.valueOf(Color.parseColor("#90A4AE"))
            button.setTextColor(Color.parseColor("#90A4AE"))
            button.strokeColor = ColorStateList.valueOf(Color.parseColor("#E0E0E0"))
            button.strokeWidth = 1
        }
    }

    private fun observeCartTotal() {
        cartViewModel.cartItems.observe(viewLifecycleOwner) { items ->
            var total = 0.0
            items.forEach { total += it.price * it.quantity }
            binding.tvTotalDeliveryPrice.text = "₹%.0f".format(total)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}