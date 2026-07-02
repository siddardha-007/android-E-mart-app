package com.example.ecommerceshop.data.repository

import androidx.lifecycle.LiveData
import com.example.ecommerceshop.data.local.OrderDao
import com.example.ecommerceshop.model.Order

class OrderRepository(
    private val orderDao: OrderDao
) {

    /**
     * Save Order
     */
    suspend fun insertOrder(order: Order) {

        orderDao.insertOrder(order)

    }

    /**
     * Get all orders of logged-in user
     */
    fun getOrders(userId: String): LiveData<List<Order>> {

        return orderDao.getOrders(userId)

    }

    /**
     * Get single order
     */
    suspend fun getOrder(orderId: Int): Order? {

        return orderDao.getOrder(orderId)

    }

    /**
     * Update order
     */
    suspend fun updateOrder(order: Order) {

        orderDao.updateOrder(order)

    }

    /**
     * Delete order
     */
    suspend fun deleteOrder(order: Order) {

        orderDao.deleteOrder(order)

    }

    /**
     * Clear all orders of logged-in user
     */
    suspend fun clearOrders(userId: String) {

        orderDao.clearOrders(userId)

    }

}