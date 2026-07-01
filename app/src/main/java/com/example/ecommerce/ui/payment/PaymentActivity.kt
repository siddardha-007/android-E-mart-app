package com.example.ecommerce.ui.payment

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.ecommerce.databinding.ActivityPaymentBinding
import com.example.ecommerce.ui.payment.PaymentSuccessActivity
import com.example.ecommerce.viewmodel.CartViewModel

class PaymentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaymentBinding

    private val cartViewModel: CartViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Payment"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        observeCart()

        setupListeners()
    }

    private fun observeCart() {

        cartViewModel.cartItems.observe(this) { items ->

            val totalAmount = items.sumOf {
                it.price * it.quantity
            }

            binding.tvTotalAmount.text = "₹ %.2f".format(totalAmount)

        }
    }

    private fun setupListeners() {

        binding.btnPlaceOrder.setOnClickListener {

            val paymentMethod = when {

                binding.rbUpi.isChecked -> "UPI"

                binding.rbCard.isChecked -> "Card"

                binding.rbCod.isChecked -> "Cash on Delivery"

                else -> ""
            }

            if (paymentMethod.isEmpty()) {

                Toast.makeText(
                    this,
                    "Please select a payment method",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            // Clear Room Database
            cartViewModel.clearCart()

            // Open Success Screen
            startActivity(
                Intent(
                    this,
                    PaymentSuccessActivity::class.java
                )
            )

            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}