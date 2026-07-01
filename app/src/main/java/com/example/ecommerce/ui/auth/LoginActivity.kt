package com.example.ecommerce.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ecommerce.databinding.ActivityLoginBinding
import com.example.ecommerce.ui.auth.OtpActivity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private var storedVerificationId: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.btnNext.setOnClickListener {
            val rawInput = binding.etPhone.text.toString().trim()

            // 1. Force remove all accidental spaces, brackets, or dashes
            var cleanedNumber = rawInput.replace("\\s+".toRegex(), "")
                .replace("-", "")
                .replace("(", "")
                .replace(")", "")

            // 2. Fallback check: If the user forgot to type '+', append it automatically if it's a 12 digit number
            // Or change "+91" to whatever country code matches your region!
            if (!cleanedNumber.startsWith("+")) {
                cleanedNumber = "+91$cleanedNumber"
            }

            // Print to Logcat to confirm exactly what string format Firebase is receiving
            Log.d("FIREBASE_AUTH", "Cleaned Number string sent: $cleanedNumber")

            if (cleanedNumber.length < 11) {
                Toast.makeText(this, "Please enter a valid phone number with country code.", Toast.LENGTH_LONG).show()
            } else {
                startPhoneVerification(cleanedNumber)
            }
        }

        binding.tvSignUp.setOnClickListener {
            //navigateToSignUp()
        }
    }

    private fun startPhoneVerification(phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber) // Must include country code (e.g., +1 or +91)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // Instant verification mechanism if applicable
        }

        override fun onVerificationFailed(e: FirebaseException) {
            Toast.makeText(this@LoginActivity, "Verification Failed: ${e.message}", Toast.LENGTH_LONG).show()
            Log.d("FIREBASE_AUTH","Verification failed ${e.message}");
        }

        override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
            storedVerificationId = verificationId

            // Send the phone number and verification id to the OTP Screen
            val intent = Intent(this@LoginActivity, OtpActivity::class.java).apply {
                putExtra("VER_ID", storedVerificationId)
                putExtra("PHONE_NUM", binding.etPhone.text.toString().trim())
            }
            startActivity(intent)
        }
    }




}