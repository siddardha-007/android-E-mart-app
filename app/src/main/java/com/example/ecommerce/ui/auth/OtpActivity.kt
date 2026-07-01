package com.example.ecommerce.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ecommerce.databinding.ActivityOtpBinding
import com.example.ecommerce.ui.main.MainActivity
import com.example.ecommerce.utils.SessionManager
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.random.Random

class OtpActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOtpBinding
    private lateinit var auth: FirebaseAuth
    private var verificationId: String? = null
    private var phoneNumber: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        verificationId = intent.getStringExtra("VER_ID")
        phoneNumber = intent.getStringExtra("PHONE_NUM")

        // Navigation Action: Go to Home Dashboard screen
        binding.btnNext.setOnClickListener {
            // Append the 6 unique input EditText blocks into a uniform code string
            val verificationCode = (
                    "${binding.etOtp1.text}" +
                            "${binding.etOtp2.text}" +
                            "${binding.etOtp3.text}" +
                            "${binding.etOtp4.text}" +
                            "${binding.etOtp5.text}" +
                            "${binding.etOtp6.text}"
                    ).trim()

            // 2. Updated validation check length from 4 to 6
            if (verificationCode.length < 6 || verificationId == null) {
                Toast.makeText(this, "Please enter the complete 6-digit verification code", Toast.LENGTH_SHORT).show()
            } else {
                signInWithPhoneAuthCredential(verificationCode)
            }
        }

        // Resend Verification Code logic hook (Moved completely clean outside of btnNext)
        binding.tvResend.setOnClickListener {
            Toast.makeText(this, "OTP Code Resent!", Toast.LENGTH_SHORT).show()
        }
    }


    private fun signInWithPhoneAuthCredential(code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)

        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = task.result?.user
                    if (user != null) {
                        saveUserToFirestore(user.uid)
                    }
                } else {
                    Toast.makeText(this, "Invalid verification code entered. Please try again.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun saveUserToFirestore(uid: String) {
        val sessionManager = SessionManager(this)

        // Generate formatting placeholders as requested
        val randomNum = Random.Default.nextInt(100, 999)
        val generatedName = "User_$randomNum"
        val cleanPhone = phoneNumber ?: "Unknown"

        // Construct Map schema object for Cloud Firestore
        val userMap = hashMapOf(
            "uniqueId" to uid,
            "mobileNumber" to cleanPhone,
            "userName" to generatedName,
            "createdAt" to Timestamp.Companion.now()
        )

        // Write user profile metadata down to Firebase Firestore Database
        FirebaseFirestore.getInstance().collection("Users")
            .document(uid)
            .set(userMap)
            .addOnSuccessListener {
                // Synchronize runtime profile flags to SharedPreferences local context
                sessionManager.isLoggedIn = true
                sessionManager.userId = uid
                sessionManager.userName = generatedName
                sessionManager.phoneNumber = cleanPhone

                // Direct user instantly to Dashboard view with zero history stacked behind it
                val intent = Intent(this, MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                startActivity(intent)
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to write profile details: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }

}