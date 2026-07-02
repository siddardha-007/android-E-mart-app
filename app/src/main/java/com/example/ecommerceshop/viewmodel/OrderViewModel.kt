package com.example.ecommerceshop.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.ecommerceshop.data.local.OrderDatabase
import com.example.ecommerceshop.data.repository.OrderRepository
import com.example.ecommerceshop.model.Order
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class OrderViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: OrderRepository

    private val userId =
        FirebaseAuth.getInstance().currentUser?.uid ?: ""

    val orders: LiveData<List<Order>>

    init {

        val orderDao = OrderDatabase
            .getDatabase(application)
            .orderDao()

        repository = OrderRepository(orderDao)

        orders = repository.getOrders(userId)

    }

    /**
     * Save Order
     */
    fun placeOrder(order: Order) {

        viewModelScope.launch {

            repository.insertOrder(order)

        }

    }

    /**
     * Delete Order
     */
    fun deleteOrder(order: Order) {

        viewModelScope.launch {

            repository.deleteOrder(order)

        }

    }

    /**
     * Clear Order History
     */
    fun clearOrders() {

        viewModelScope.launch {

            repository.clearOrders(userId)

        }

    }

}