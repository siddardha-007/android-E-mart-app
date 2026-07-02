package com.example.ecommerceshop.ui.emailauth

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ecommerceshop.databinding.ActivityEmailLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.example.ecommerceshop.ui.main.MainActivity
import com.example.ecommerceshop.utils.PreferenceManager
import com.google.firebase.firestore.FirebaseFirestore
class EmailLoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmailLoginBinding

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEmailLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener {

            loginUser()

        }

        binding.tvCreateAccount.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    RegisterActivity::class.java
                )
            )

        }

    }

    private fun loginUser() {

        val email = binding.etEmail.text.toString().trim()

        val password = binding.etPassword.text.toString().trim()

        if (email.isEmpty()) {

            binding.etEmail.error = "Enter email"

            return

        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

            binding.etEmail.error = "Invalid email"

            return

        }

        if (password.isEmpty()) {

            binding.etPassword.error = "Enter password"

            return

        }

        binding.progressBar.visibility = View.VISIBLE

        firebaseAuth.signInWithEmailAndPassword(
            email,
            password
        )
            .addOnCompleteListener { task ->

                binding.progressBar.visibility = View.GONE

                if (task.isSuccessful()) {

                    val uid = firebaseAuth.currentUser!!.uid

                    FirebaseFirestore.getInstance()
                        .collection("users")
                        .document(uid)
                        .get()
                        .addOnSuccessListener { document ->

                            val name = document.getString("name") ?: "Guest"
                            val email = document.getString("email") ?: ""

                            PreferenceManager(this).saveUserDetails(
                                name,
                                email
                            )

                            Toast.makeText(
                                this,
                                "Login Successful",
                                Toast.LENGTH_SHORT
                            ).show()

                            startActivity(
                                Intent(
                                    this,
                                    MainActivity::class.java
                                )
                            )

                            finish()

                        }
                        .addOnFailureListener {

                            Toast.makeText(
                                this,
                                "Failed to load user data",
                                Toast.LENGTH_SHORT
                            ).show()

                        }

                } else {

                    Toast.makeText(
                        this,
                        task.exception?.message,
                        Toast.LENGTH_LONG
                    ).show()

                }

            }

    }

}