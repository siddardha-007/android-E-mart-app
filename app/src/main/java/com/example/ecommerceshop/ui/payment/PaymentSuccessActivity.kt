package com.example.ecommerceshop.ui.payment

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.ecommerceshop.databinding.ActivityPaymentSuccessBinding

class PaymentSuccessActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaymentSuccessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPaymentSuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()



        binding.btnContinueShopping.text = "View Receipt"

        binding.btnContinueShopping.setOnClickListener {

            openReceipt()

        }

        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {

                override fun handleOnBackPressed() {

                    openReceipt()

                }

            }
        )

    }

    private fun openReceipt() {

        val intent = Intent(
            this,
            ReceiptActivity::class.java
        )

        intent.putExtra(
            "CUSTOMER_NAME",
            getIntent().getStringExtra("CUSTOMER_NAME")
        )

        intent.putExtra(
            "CUSTOMER_PHONE",
            getIntent().getStringExtra("CUSTOMER_PHONE")
        )

        intent.putExtra(
            "CUSTOMER_ADDRESS",
            getIntent().getStringExtra("CUSTOMER_ADDRESS")
        )

        intent.putExtra(
            "ADDRESS_TYPE",
            getIntent().getStringExtra("ADDRESS_TYPE")
        )

        intent.putExtra(
            "PAYMENT_MODE",
            getIntent().getStringExtra("PAYMENT_MODE")
        )

        intent.putExtra(
            "TOTAL_ITEMS",
            getIntent().getIntExtra("TOTAL_ITEMS", 0)
        )

        intent.putExtra(
            "TOTAL_AMOUNT",
            getIntent().getDoubleExtra("TOTAL_AMOUNT", 0.0)
        )

        startActivity(intent)

        finish()

    }

}