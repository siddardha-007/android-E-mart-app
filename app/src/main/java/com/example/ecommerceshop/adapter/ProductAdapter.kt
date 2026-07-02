package com.example.ecommerceshop.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerceshop.databinding.ItemProductBinding
import com.example.ecommerceshop.model.Product

class ProductAdapter(
    private var productList: List<Product>,
    private val onWishlistClick: ((Product) -> Unit)? = null,
    private val onAddCartClick: ((Product) -> Unit)? = null,
    private val onItemClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(
        private val binding: ItemProductBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            // Title & Price bindings correctly matching Indian Rupee symbol format
            binding.tvTitle.text = product.title
            binding.tvPrice.text = "₹${product.price}"

            // Strikethrough execution for original retail pricing references
            binding.tvOriginalPrice.text = "₹${product.price * 2}" // Replace with product.originalPrice if available
            binding.tvOriginalPrice.paintFlags = binding.tvOriginalPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

            // Dynamic badge computation or display fallback logic
            binding.tvDiscountBadge.text = "52%\nOff"

            if (product.images.isNotEmpty()) {
                Glide.with(binding.root.context)
                    .load(product.images[0])
                    .fitCenter() // Changed to fitCenter to match item display guidelines
                    .into(binding.ivProduct)
            }

            // Click Bindings for Child Elements
            binding.root.setOnClickListener { onItemClick(product) }

            binding.ivWishlist.setOnClickListener {
                onWishlistClick?.invoke(product)
            }

            binding.btnAddAction.setOnClickListener {
                onAddCartClick?.invoke(product)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    override fun getItemCount(): Int = productList.size

    fun updateProducts(newProducts: List<Product>) {
        productList = newProducts
        notifyDataSetChanged()
    }
}