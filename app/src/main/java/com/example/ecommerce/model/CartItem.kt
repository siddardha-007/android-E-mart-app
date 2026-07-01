package com.example.ecommerce.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItem(

    @PrimaryKey
    val productId: Int,

    val title: String,

    val image: String,

    val price: Double,

    var quantity: Int = 1
)