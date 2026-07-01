package com.example.ecommerceshop.data.api

import com.example.ecommerceshop.model.Category
import com.example.ecommerceshop.model.Product
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    // Get all products
    @GET("products")
    suspend fun getAllProducts(): Response<List<Product>>

    // Get a single product by ID
    @GET("products/{id}")
    suspend fun getProductById(
        @Path("id") id: Int
    ): Response<Product>


    // Get all categories
    @GET("categories")
    suspend fun getAllCategories(): Response<List<Category>>

    // Get products by category ID
    @GET("categories/{id}/products")
    suspend fun getProductsByCategory(
        @Path("id") categoryId: Int
    ): Response<List<Product>>
}