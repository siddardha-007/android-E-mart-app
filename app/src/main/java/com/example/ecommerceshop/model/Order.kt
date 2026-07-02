package com.example.ecommerceshop.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class Order(

    @PrimaryKey(autoGenerate = true)
    val orderId: Int = 0,

    // User Details
    val userId: String,
    val userName: String,
    val userEmail: String,

    // Customer Details
    val customerName: String,
    val customerPhone: String,
    val customerAddress: String,
    val addressType: String,

    // Payment
    val paymentMode: String,

    // Order Summary
    val totalItems: Int,
    val totalAmount: Double

)