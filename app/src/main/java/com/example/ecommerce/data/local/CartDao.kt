package com.example.ecommerce.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.ecommerce.model.CartItem

@Dao
interface CartDao {

    /**
     * Insert a new item into the cart.
     * If the product already exists, replace it.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(cartItem: CartItem)

    /**
     * Get all cart items.
     */
    @Query("SELECT * FROM cart_items")
    fun getCartItems(): LiveData<List<CartItem>>

    /**
     * Update an existing cart item.
     */
    @Update
    suspend fun updateCartItem(cartItem: CartItem)

    /**
     * Delete a single cart item.
     */
    @Delete
    suspend fun deleteCartItem(cartItem: CartItem)

    /**
     * Delete all items from the cart.
     */
    @Query("DELETE FROM cart_items")
    suspend fun clearCart()

    /**
     * Get a single item by product ID.
     */
    @Query("SELECT * FROM cart_items WHERE productId = :productId LIMIT 1")
    suspend fun getCartItemById(productId: Int): CartItem?

    /**
     * Get total number of items in the cart.
     */
    @Query("SELECT COUNT(*) FROM cart_items")
    fun getCartItemCount(): LiveData<Int>
}