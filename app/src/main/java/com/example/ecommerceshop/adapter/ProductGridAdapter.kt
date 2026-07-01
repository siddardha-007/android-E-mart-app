package com.example.ecommerceshop.adapter

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerceshop.databinding.ItemProductGridBinding
import com.example.ecommerceshop.model.CartItem
import com.example.ecommerceshop.model.Product

class ProductGridAdapter(
    private var products: List<Product>,
    private val onProductClick: (Product) -> Unit,
    private val onAddToCartClick: (CartItem) -> Unit,
    private val onWishlistClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductGridAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(
        private val binding: ItemProductGridBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            // Set up title description
            binding.tvTitle.text = product.title

            // Price formatting matching Indian Rupee format shown in Figma
            binding.tvPrice.text = "₹%.0f".format(product.price)

            // Setup Demo crossed-out pricing matching Figma layout
            val mockOriginalPrice = product.price * 2
            binding.tvOriginalPrice.text = "₹%.0f".format(mockOriginalPrice)

            // Apply native text flag to strike through old original price string
            binding.tvOriginalPrice.paintFlags = binding.tvOriginalPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

            // Bind matching text badges
            binding.tvDiscount.text = "52%\nOff"
            binding.tvDiscountPercent.text = "52% Off"

            // Glide configuration matching the crisp product image positioning
            Glide.with(binding.root.context)
                .load(product.images.firstOrNull())
                .fitCenter()
                .into(binding.ivProduct)

            // Actions setup block
            binding.root.setOnClickListener {
                onProductClick(product)
            }

            binding.btnWishlist.setOnClickListener {
                onWishlistClick(product)
            }

            binding.btnAddCart.setOnClickListener {
                val cartItem = CartItem(
                    productId = product.id,
                    title = product.title,
                    image = product.images.firstOrNull() ?: "",
                    price = product.price,
                    quantity = 1
                )
                onAddToCartClick(cartItem)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductGridBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount(): Int = products.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateProducts(newProducts: List<Product>) {
        this.products = newProducts
        notifyDataSetChanged()
    }
}