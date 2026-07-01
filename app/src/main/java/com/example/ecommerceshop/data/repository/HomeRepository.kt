package com.example.ecommerceshop.data.repository

import com.example.ecommerceshop.data.api.RetrofitInstance
import com.example.ecommerceshop.model.Banner

class HomeRepository {

    // Products
    suspend fun getAllProducts() =
        RetrofitInstance.api.getAllProducts()

    // Categories
    suspend fun getAllCategories() =
        RetrofitInstance.api.getAllCategories()

    // Products by Category
    suspend fun getProductsByCategory(categoryId: Int) =
        RetrofitInstance.api.getProductsByCategory(categoryId)

    /**
     * Temporary Local Banners
     * Later these can be fetched from Firebase Firestore.
     */
    fun getBanners(): List<Banner> {

        return listOf(

            Banner(
                imageUrl = "https://images.unsplash.com/photo-1542838132-92c53300491e",
                title = "Fresh Vegetables",
                description = "Up to 40% OFF"
            ),

            Banner(
                imageUrl = "https://images.unsplash.com/photo-1506617420156-8e4536971650",
                title = "Summer Sale",
                description = "Buy 1 Get 1 Free"
            ),

            Banner(
                imageUrl = "https://images.unsplash.com/photo-1511556820780-d912e42b4980",
                title = "Electronics",
                description = "Starting at ₹999"
            )

        )
    }
}