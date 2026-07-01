package com.example.ecommerceshop.ui.products

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.ecommerceshop.databinding.ActivityProductDetailsBinding
import com.example.ecommerceshop.model.CartItem
import com.example.ecommerceshop.model.Product
import com.example.ecommerceshop.viewmodel.CartViewModel

class ProductDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailsBinding
    private val cartViewModel: CartViewModel by viewModels()
    private lateinit var product: Product

    private var quantityCounter = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getProduct()
        setProductData()
        setupCounterAndClicks()
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
        binding.tvCategory.text = "Categories : ${product.category.name}"

        val basePriceString = "₹%.0f".format(product.price)
        binding.tvPrice.text = "$basePriceString / 1Kg"
        binding.tvTotalPrice.text = basePriceString

        binding.tvDiscountPercent.text = "52% OFF ends in 3 days"

        // Load images into primary display container and side previews thumbnail fields
        val firstImage = product.images.firstOrNull()

        Glide.with(this).load(firstImage).fitCenter().into(binding.ivProduct)
        Glide.with(this).load(firstImage).fitCenter().into(binding.ivThumb1)

        if (product.images.size > 1) {
            Glide.with(this).load(product.images[1]).fitCenter().into(binding.ivThumb2)
        } else {
            Glide.with(this).load(firstImage).fitCenter().into(binding.ivThumb2)
        }
    }

    private fun setupCounterAndClicks() {
        binding.btnBack.setOnClickListener { finish() }

        binding.btnWishlist.setOnClickListener {
            Toast.makeText(this, "Added to Wishlist", Toast.LENGTH_SHORT).show()
        }

        // Increment quantity logic loop counter block
        binding.btnIncrement.setOnClickListener {
            quantityCounter++
            updateCounterUI()
        }

        // Decrement quantity logic loop counter block
        binding.btnDecrement.setOnClickListener {
            if (quantityCounter > 1) {
                quantityCounter--
                updateCounterUI()
            } else {
                // Remove item / set to 0 configuration action
                Toast.makeText(this, "Minimum order limit reached", Toast.LENGTH_SHORT).show()
            }
        }

        // Trigger cart dispatch on counter container pill click
        binding.btnCounterContainer.setOnClickListener {
            val cartItem = CartItem(
                productId = product.id,
                title = product.title,
                image = product.images.firstOrNull() ?: "",
                price = product.price,
                quantity = quantityCounter
            )
            cartViewModel.addToCart(cartItem)
            Toast.makeText(this, "$quantityCounter item(s) pushed to Cart", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateCounterUI() {
        binding.tvCounterValue.text = "$quantityCounter pcs"
        val updatedTotalPrice = product.price * quantityCounter
        binding.tvTotalPrice.text = "₹%.0f".format(updatedTotalPrice)
    }
}