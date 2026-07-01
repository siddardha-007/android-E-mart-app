package com.example.ecommerce.ui.products

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.ecommerce.databinding.ActivityProductDetailsBinding
import com.example.ecommerce.model.CartItem
import com.example.ecommerce.model.Product
import com.example.ecommerce.ui.payment.PaymentActivity
import com.example.ecommerce.viewmodel.CartViewModel

class ProductDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailsBinding

    private val cartViewModel: CartViewModel by viewModels()

    private lateinit var product: Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getProduct()

        setProductData()

        setupClickListeners()
    }

    private fun getProduct() {

        product = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("product", Product::class.java)!!
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra("product") as Product
        }

    }

    private fun setProductData() {

        binding.tvTitle.text = product.title

        binding.tvCategory.text = product.category.name

        binding.tvPrice.text = "$${product.price}"

        binding.tvDescription.text = product.description

        Glide.with(this)
            .load(product.images.firstOrNull())
            .placeholder(com.example.ecommerce.R.drawable.banner_placeholder)
            .into(binding.ivProduct)

    }

    private fun setupClickListeners() {

        binding.btnBack.setOnClickListener {

            finish()

        }

        binding.btnCart.setOnClickListener {

            finish()

        }

        binding.btnAddToCart.setOnClickListener {

            val cartItem = CartItem(
                productId = product.id,
                title = product.title,
                image = product.images.firstOrNull() ?: "",
                price = product.price,
                quantity = 1
            )

            cartViewModel.addToCart(cartItem)

            Toast.makeText(
                this,
                "Added to Cart",
                Toast.LENGTH_SHORT
            ).show()
        }
        binding.btnBuyNow.setOnClickListener {

            val intent = Intent(
                this,
                PaymentActivity::class.java
            )

            startActivity(intent)

        }

    }
}