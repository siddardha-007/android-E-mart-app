package com.example.ecommerceshop.ui.emailauth

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ecommerceshop.databinding.ActivityRegisterBinding
import com.example.ecommerceshop.ui.main.MainActivity
import com.example.ecommerceshop.utils.PreferenceManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        binding.btnRegister.setOnClickListener {

            registerUser()

        }

        binding.tvLogin.setOnClickListener {

            finish()

        }

    }

    private fun registerUser() {

        val name = binding.etName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        val confirmPassword = binding.etConfirmPassword.text.toString().trim()

        if (name.isEmpty()) {
            binding.etName.error = "Enter Name"
            return
        }

        if (email.isEmpty()) {
            binding.etEmail.error = "Enter Email"
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.error = "Invalid Email"
            return
        }

        if (password.length < 6) {
            binding.etPassword.error = "Minimum 6 characters"
            return
        }

        if (password != confirmPassword) {
            binding.etConfirmPassword.error = "Passwords do not match"
            return
        }

        binding.progressBar.visibility = View.VISIBLE

        firebaseAuth.createUserWithEmailAndPassword(email, password)

            .addOnCompleteListener { task ->

                if (task.isSuccessful) {

                    val uid = firebaseAuth.currentUser!!.uid

                    firestore.collection("users")
                        .get()
                        .addOnSuccessListener { documents ->

                            val username = String.format(
                                "User_%03d",
                                documents.size() + 1
                            )

                            val user = hashMapOf(

                                "uid" to uid,

                                "name" to name,

                                "username" to username,

                                "email" to email,

                                "createdAt" to com.google.firebase.Timestamp.now()

                            )

                            firestore.collection("users")
                                .document(uid)
                                .set(user)
                                .addOnSuccessListener {

                                    binding.progressBar.visibility = View.GONE

                                    Toast.makeText(
                                        this,
                                        "Registration Successful",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    PreferenceManager(this).saveUserDetails(
                                        name,
                                        email
                                    )

                                    startActivity(
                                        Intent(
                                            this,
                                            MainActivity::class.java
                                        )
                                    )

                                    finishAffinity()

                                }
                                .addOnFailureListener {

                                    binding.progressBar.visibility = View.GONE

                                    Toast.makeText(
                                        this,
                                        it.message,
                                        Toast.LENGTH_SHORT
                                    ).show()

                                }

                        }

                } else {

                    binding.progressBar.visibility = View.GONE

                    Toast.makeText(
                        this,
                        task.exception?.message,
                        Toast.LENGTH_LONG
                    ).show()

                }

            }

    }

}