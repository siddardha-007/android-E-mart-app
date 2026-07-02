package com.example.ecommerceshop.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.ecommerceshop.model.CartItem

@Dao
interface CartDao {

    /**
     * Insert Cart Item
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(cartItem: CartItem)

    /**
     * Get Cart Items of Logged-in User
     */
    @Query("SELECT * FROM cart_items WHERE userId = :userId")
    fun getCartItems(userId: String): LiveData<List<CartItem>>

    /**
     * Update Cart Item
     */
    @Update
    suspend fun updateCartItem(cartItem: CartItem)

    /**
     * Delete Single Item
     */
    @Delete
    suspend fun deleteCartItem(cartItem: CartItem)

    /**
     * Clear Cart of Logged-in User
     */
    @Query("DELETE FROM cart_items WHERE userId = :userId")
    suspend fun clearCart(userId: String)

    /**
     * Get Existing Product of Logged-in User
     */
    @Query("""
        SELECT * FROM cart_items
        WHERE userId = :userId
        AND productId = :productId
        LIMIT 1
    """)
    suspend fun getCartItemById(
        userId: String,
        productId: Int
    ): CartItem?

    /**
     * Get Cart Count of Logged-in User
     */
    @Query("SELECT COUNT(*) FROM cart_items WHERE userId = :userId")
    fun getCartItemCount(userId: String): LiveData<Int>

}