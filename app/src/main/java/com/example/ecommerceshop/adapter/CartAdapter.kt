package com.example.ecommerceshop.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerceshop.databinding.ItemCartBinding
import com.example.ecommerceshop.model.CartItem

class CartAdapter(
    private var cartItems: List<CartItem>,
    private val onIncrease: (CartItem) -> Unit,
    private val onDecrease: (CartItem) -> Unit,
    private val onDelete: (CartItem) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(
        private val binding: ItemCartBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(cartItem: CartItem) {
            // Bind Title
            binding.tvCartItemTitle.text = cartItem.title

            // Bind Price (Figma format: Current Price followed by Strike-through Original Price)
            binding.tvCartItemPrice.text = "₹%.0f,".format(cartItem.price)

            // Mirroring the UI decoration by creating an illustrative fake original baseline price
            val fakeOriginalPrice = cartItem.price * 2
            binding.tvCartItemOriginalPrice.text = "₹%.0f".format(fakeOriginalPrice)

            // Bind Quantity counter text (e.g., "1 pcs")
            binding.tvCartQuantityValue.text = "${cartItem.quantity} pcs"

            // Optional Design Extra: Toggle the unique "Suggestion" or "Seggestion" item badge flag
            // if the title matches or based on your custom model properties
            if (cartItem.title.contains("Suggestion", ignoreCase = true) ||
                cartItem.title.contains("Banana", ignoreCase = true)) {
                binding.tvSuggestionBadge.visibility = View.VISIBLE
            } else {
                binding.tvSuggestionBadge.visibility = View.GONE
            }

            // Load Image cleanly using fitCenter to match the rounded boundary cards
            Glide.with(binding.root.context)
                .load(cartItem.image)
                .fitCenter()
                .into(binding.ivCartProduct)

            // Set Action Button Listeners
            binding.btnCartIncrease.setOnClickListener {
                onIncrease(cartItem)
            }

            binding.btnCartDecrease.setOnClickListener {
                // Since the new layout wraps "decrease" and "delete" inside the same sleek horizontal pill layout,
                // if the item reaches 1 and the user clicks minus, it passes through to trigger a safe reduction/deletion check
                onDecrease(cartItem)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CartViewHolder {
        val binding = ItemCartBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CartViewHolder,
        position: Int
    ) {
        holder.bind(cartItems[position])
    }

    override fun getItemCount(): Int = cartItems.size

    fun updateCartItems(items: List<CartItem>) {
        cartItems = items
        notifyDataSetChanged()
    }
}