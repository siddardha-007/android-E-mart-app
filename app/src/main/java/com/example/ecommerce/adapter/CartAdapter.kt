package com.example.ecommerce.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerce.databinding.ItemCartBinding
import com.example.ecommerce.model.CartItem

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

            binding.tvTitle.text = cartItem.title

            binding.tvPrice.text = "₹ ${cartItem.price}"

            binding.tvQuantity.text = cartItem.quantity.toString()

            val total = cartItem.price * cartItem.quantity

            binding.tvTotal.text = "₹ $total"

            Glide.with(binding.root.context)
                .load(cartItem.image)
                .centerCrop()
                .into(binding.ivProduct)

            binding.btnIncrease.setOnClickListener {
                onIncrease(cartItem)
            }

            binding.btnDecrease.setOnClickListener {
                onDecrease(cartItem)
            }

            binding.btnDelete.setOnClickListener {
                onDelete(cartItem)
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