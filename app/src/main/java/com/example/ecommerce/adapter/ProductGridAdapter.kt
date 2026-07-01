package com.example.ecommerce.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerce.databinding.ItemProductGridBinding
import com.example.ecommerce.model.CartItem
import com.example.ecommerce.model.Product

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

            binding.tvTitle.text = product.title

            binding.tvCategory.text = product.category.name

            binding.tvPrice.text = "$ %.2f".format(product.price)


            // Demo discount
            binding.tvDiscount.text = "20% OFF"

            Glide.with(binding.root.context)
                .load(product.images.firstOrNull())
                .centerCrop()
                .into(binding.ivProduct)

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

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {

        val binding = ItemProductGridBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int
    ) {
        holder.bind(products[position])
    }

    override fun getItemCount(): Int = products.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateProducts(newProducts: List<Product>) {

        products = newProducts
        notifyDataSetChanged()
    }
}