package com.example.ecommerceshop.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.ecommerceshop.model.Order

@Dao
interface OrderDao {

    /**
     * Save Order
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: Order)

    /**
     * Get all orders of logged-in user
     */
    @Query("SELECT * FROM orders WHERE userId = :userId ORDER BY orderId DESC")
    fun getOrders(userId: String): LiveData<List<Order>>

    /**
     * Get single order
     */
    @Query("SELECT * FROM orders WHERE orderId = :orderId LIMIT 1")
    suspend fun getOrder(orderId: Int): Order?

    /**
     * Update order
     */
    @Update
    suspend fun updateOrder(order: Order)

    /**
     * Delete single order
     */
    @Delete
    suspend fun deleteOrder(order: Order)

    /**
     * Delete all orders of logged-in user
     */
    @Query("DELETE FROM orders WHERE userId = :userId")
    suspend fun clearOrders(userId: String)

}