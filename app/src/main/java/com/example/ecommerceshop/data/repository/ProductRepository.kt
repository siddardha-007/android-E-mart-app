package com.example.ecommerceshop.data.repository

import com.example.ecommerceshop.data.api.RetrofitInstance
import com.example.ecommerceshop.model.Product
import retrofit2.Response

class ProductRepository {

    /**
     * Fetch all products from the API
     */
    suspend fun getAllProducts(): Response<List<Product>> {
        return RetrofitInstance.api.getAllProducts()
    }

    /**
     * Fetch a single product by its ID
     */
    suspend fun getProductById(id: Int): Response<Product> {
        return RetrofitInstance.api.getProductById(id)
    }

    suspend fun getAllCategories() =
        RetrofitInstance.api.getAllCategories()

    suspend fun getProductsByCategory(categoryId: Int) =
        RetrofitInstance.api.getProductsByCategory(categoryId)
}