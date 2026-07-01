package com.example.ecommerceshop.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerceshop.adapter.CartAdapter
import com.example.ecommerceshop.databinding.FragmentCartItemsBinding
import com.example.ecommerceshop.viewmodel.CartViewModel

class CartItemsFragment(private val onNextClicked: () -> Unit) : Fragment() {

    private var _binding: FragmentCartItemsBinding? = null
    private val binding get() = _binding!!

    // Note the activityViewModels use case so we listen to the exact same cart instance state repository
    private val cartViewModel: CartViewModel by activityViewModels()
    private lateinit var cartAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartItemsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeCart()

        binding.btnNext.setOnClickListener {
            onNextClicked()
        }
    }

    private fun setupRecyclerView() {
        cartAdapter = CartAdapter(
            emptyList(),
            onIncrease = { item ->
                cartViewModel.updateQuantity(item, item.quantity + 1)
            },
            onDecrease = { item ->
                if (item.quantity > 1) {
                    cartViewModel.updateQuantity(item, item.quantity - 1)
                } else {
                    cartViewModel.removeItem(item)
                    Toast.makeText(requireContext(), "Removed from Cart", Toast.LENGTH_SHORT).show()
                }
            },
            onDelete = { item ->
                cartViewModel.removeItem(item)
                Toast.makeText(requireContext(), "Removed from Cart", Toast.LENGTH_SHORT).show()
            }
        )

        binding.rvCartItems.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCartItems.adapter = cartAdapter
    }

    private fun observeCart() {
        cartViewModel.cartItems.observe(viewLifecycleOwner) { items ->
            cartAdapter.updateCartItems(items)

            // Toggle visibility depending on list values
            val isEmpty = items.isEmpty()
            binding.rvCartItems.visibility = if (isEmpty) View.GONE else View.VISIBLE
            binding.layoutBannerLimit.visibility = if (isEmpty) View.GONE else View.VISIBLE
            binding.btnNext.isEnabled = !isEmpty

            calculateTotal(items)
        }
    }

    private fun calculateTotal(items: List<com.example.ecommerceshop.model.CartItem>) {
        var total = 0.0
        items.forEach { total += it.price * it.quantity }

        // Exact styling match for Figma prices
        binding.tvTotalCartPrice.text = "₹%.0f".format(total)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}