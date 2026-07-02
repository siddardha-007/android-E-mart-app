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
import androidx.fragment.app.viewModels
import com.example.ecommerceshop.databinding.FragmentDeliveryDetailsBinding
import com.example.ecommerceshop.model.Order
import com.example.ecommerceshop.ui.payment.PaymentSuccessActivity
import com.example.ecommerceshop.utils.PreferenceManager
import com.example.ecommerceshop.viewmodel.CartViewModel
import com.example.ecommerceshop.viewmodel.OrderViewModel
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth

class DeliveryDetailsFragment : Fragment() {

    private var _binding: FragmentDeliveryDetailsBinding? = null
    private val binding get() = _binding!!

    private val cartViewModel: CartViewModel by activityViewModels()

    private val orderViewModel: OrderViewModel by viewModels()

    private var selectedAddressType = "Office"

    private var selectedPaymentMode = "Cash"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDeliveryDetailsBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root

    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        observeCartTotal()

        setupSelectionListeners()

        binding.btnCheckout.setOnClickListener {

            placeOrder()

        }

    }

    private fun placeOrder() {

        val customerName =
            binding.etCustomerName.text.toString().trim()

        val customerPhone =
            binding.etCustomerMobile.text.toString().trim()

        val customerAddress =
            binding.etCustomerAddress.text.toString().trim()

        if (customerName.isEmpty()) {

            binding.etCustomerName.error = "Enter Customer Name"

            return

        }

        if (customerPhone.isEmpty()) {

            binding.etCustomerMobile.error = "Enter Mobile Number"

            return

        }

        if (customerAddress.isEmpty()) {

            binding.etCustomerAddress.error = "Enter Address"

            return

        }

        val firebaseUser =
            FirebaseAuth.getInstance().currentUser

        if (firebaseUser == null) {

            Toast.makeText(
                requireContext(),
                "Please Login",
                Toast.LENGTH_SHORT
            ).show()

            return

        }

        val preferenceManager =
            PreferenceManager(requireContext())

        val cartItems =
            cartViewModel.cartItems.value ?: emptyList()

        val totalItems =
            cartItems.sumOf { it.quantity }

        val totalAmount =
            cartViewModel.calculateTotal(cartItems)

        val order = Order(

            userId = firebaseUser.uid,

            userName = preferenceManager.getUserName(),

            userEmail = preferenceManager.getUserEmail(),

            customerName = customerName,

            customerPhone = customerPhone,

            customerAddress = customerAddress,

            addressType = selectedAddressType,

            paymentMode = selectedPaymentMode,

            totalItems = totalItems,

            totalAmount = totalAmount

        )

        orderViewModel.placeOrder(order)

        cartViewModel.clearCart()

        val intent = Intent(
            requireContext(),
            PaymentSuccessActivity::class.java
        )

        intent.putExtra("CUSTOMER_NAME", customerName)
        intent.putExtra("CUSTOMER_PHONE", customerPhone)
        intent.putExtra("CUSTOMER_ADDRESS", customerAddress)
        intent.putExtra("ADDRESS_TYPE", selectedAddressType)
        intent.putExtra("PAYMENT_MODE", selectedPaymentMode)
        intent.putExtra("TOTAL_ITEMS", totalItems)
        intent.putExtra("TOTAL_AMOUNT", totalAmount)

        startActivity(intent)

    }

    private fun setupSelectionListeners() {

        binding.btnTypeOffice.setOnClickListener {

            selectedAddressType = "Office"

            updateButtonSelectionState(
                binding.btnTypeOffice,
                true
            )

            updateButtonSelectionState(
                binding.btnTypeHome,
                false
            )

        }

        binding.btnTypeHome.setOnClickListener {

            selectedAddressType = "Home"

            updateButtonSelectionState(
                binding.btnTypeHome,
                true
            )

            updateButtonSelectionState(
                binding.btnTypeOffice,
                false
            )

        }

        binding.btnPaymentCash.setOnClickListener {

            selectedPaymentMode = "Cash"

            updateButtonSelectionState(
                binding.btnPaymentCash,
                true
            )

            updateButtonSelectionState(
                binding.btnPaymentUpi,
                false
            )

        }

        binding.btnPaymentUpi.setOnClickListener {

            selectedPaymentMode = "UPI"

            updateButtonSelectionState(
                binding.btnPaymentUpi,
                true
            )

            updateButtonSelectionState(
                binding.btnPaymentCash,
                false
            )

        }

    }

    private fun updateButtonSelectionState(
        button: MaterialButton,
        isSelected: Boolean
    ) {

        if (isSelected) {

            button.backgroundTintList =
                ColorStateList.valueOf(
                    Color.parseColor("#2A83A2")
                )

            button.iconTint =
                ColorStateList.valueOf(Color.WHITE)

            button.setTextColor(Color.WHITE)

            button.strokeWidth = 0

        } else {

            button.backgroundTintList =
                ColorStateList.valueOf(Color.WHITE)

            button.iconTint =
                ColorStateList.valueOf(
                    Color.parseColor("#90A4AE")
                )

            button.setTextColor(
                Color.parseColor("#90A4AE")
            )

            button.strokeColor =
                ColorStateList.valueOf(
                    Color.parseColor("#E0E0E0")
                )

            button.strokeWidth = 1

        }

    }

    private fun observeCartTotal() {

        cartViewModel.cartItems.observe(viewLifecycleOwner) { items ->

            val total =
                cartViewModel.calculateTotal(items)

            binding.tvTotalDeliveryPrice.text =
                "₹%.0f".format(total)

        }

    }

    override fun onDestroyView() {

        super.onDestroyView()

        _binding = null

    }

}