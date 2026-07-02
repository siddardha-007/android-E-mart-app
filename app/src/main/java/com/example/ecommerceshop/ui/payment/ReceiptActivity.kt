package com.example.ecommerceshop.ui.payment

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.ecommerceshop.databinding.ActivityReceiptBinding
import com.example.ecommerceshop.ui.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ReceiptActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReceiptBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityReceiptBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        loadReceipt()

        binding.btnContinueShopping.setOnClickListener {

            navigateToHome()

        }

        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {

                    navigateToHome()

                }
            }
        )

    }

    private fun loadReceipt() {

        val customerName =
            intent.getStringExtra("CUSTOMER_NAME") ?: ""

        val customerPhone =
            intent.getStringExtra("CUSTOMER_PHONE") ?: ""

        val customerAddress =
            intent.getStringExtra("CUSTOMER_ADDRESS") ?: ""

        val addressType =
            intent.getStringExtra("ADDRESS_TYPE") ?: ""

        val paymentMode =
            intent.getStringExtra("PAYMENT_MODE") ?: ""

        val totalItems =
            intent.getIntExtra("TOTAL_ITEMS", 0)

        val totalAmount =
            intent.getDoubleExtra("TOTAL_AMOUNT", 0.0)

        val email =
            FirebaseAuth.getInstance().currentUser?.email ?: ""

        val orderId =
            "#${System.currentTimeMillis().toString().takeLast(6)}"

        val date =
            SimpleDateFormat(
                "dd MMM yyyy hh:mm a",
                Locale.getDefault()
            ).format(Date())

        binding.tvOrderId.text = orderId
        binding.tvCustomerName.text = customerName
        binding.tvEmail.text = email
        binding.tvPhone.text = customerPhone
        binding.tvAddress.text = customerAddress
        binding.tvAddressType.text = addressType
        binding.tvPaymentMode.text = paymentMode
        binding.tvItems.text = totalItems.toString()
        binding.tvTotalAmount.text = "₹%.2f".format(totalAmount)
        binding.tvDate.text = date

    }

    private fun navigateToHome() {

        val intent = Intent(
            this,
            MainActivity::class.java
        )

        intent.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TASK

        startActivity(intent)

        finish()

    }

}