package com.example.ecommerce.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerce.adapter.CartAdapter
import com.example.ecommerce.databinding.FragmentCartBinding
import com.example.ecommerce.ui.payment.PaymentActivity
import com.example.ecommerce.viewmodel.CartViewModel

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private val cartViewModel: CartViewModel by viewModels()

    private lateinit var cartAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCartBinding.inflate(inflater, container, false)

        setupRecyclerView()

        observeCart()

        setupListeners()

        return binding.root
    }

    private fun setupRecyclerView() {

        cartAdapter = CartAdapter(

            emptyList(),

            onIncrease = {

                cartViewModel.updateQuantity(
                    it,
                    it.quantity + 1
                )

            },

            onDecrease = {

                if (it.quantity > 1) {

                    cartViewModel.updateQuantity(
                        it,
                        it.quantity - 1
                    )

                } else {

                    cartViewModel.removeItem(it)

                }

            },

            onDelete = {

                cartViewModel.removeItem(it)

                Toast.makeText(
                    requireContext(),
                    "Removed from Cart",
                    Toast.LENGTH_SHORT
                ).show()

            }

        )

        binding.rvCart.layoutManager =
            LinearLayoutManager(requireContext())

        binding.rvCart.adapter = cartAdapter

    }

    private fun observeCart() {

        cartViewModel.cartItems.observe(viewLifecycleOwner) { items ->

            cartAdapter.updateCartItems(items)

            binding.layoutEmpty.visibility =
                if (items.isEmpty()) View.VISIBLE else View.GONE

            calculateTotal(items)

        }

    }

    private fun calculateTotal(items: List<com.example.ecommerce.model.CartItem>) {

        var total = 0.0

        items.forEach {

            total += it.price * it.quantity

        }

        binding.tvTotal.text =
            "Total : ₹ %.2f".format(total)

    }

    private fun setupListeners() {

        binding.btnCheckout.setOnClickListener {

            startActivity(

                Intent(
                    requireContext(),
                    PaymentActivity::class.java
                )

            )

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}