package com.example.ecommerce.model

import java.io.Serializable

data class Product(

    val id: Int,

    val title: String,

    val price: Double,

    val description: String,

    val images: List<String>,

    val category: Category

) : Serializable