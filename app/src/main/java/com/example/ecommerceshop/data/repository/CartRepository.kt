package com.example.ecommerceshop.data.repository

import androidx.lifecycle.LiveData
import com.example.ecommerceshop.data.local.CartDao
import com.example.ecommerceshop.model.CartItem

class CartRepository(
    private val cartDao: CartDao
) {

    /**
     * Get all cart items of a user
     */
    fun getCartItems(userId: String): LiveData<List<CartItem>> {

        return cartDao.getCartItems(userId)

    }

    /**
     * Get cart count of a user
     */
    fun getCartItemCount(userId: String): LiveData<Int> {

        return cartDao.getCartItemCount(userId)

    }

    /**
     * Insert item
     */
    suspend fun insertCartItem(cartItem: CartItem) {

        cartDao.insertCartItem(cartItem)

    }

    /**
     * Update item
     */
    suspend fun updateCartItem(cartItem: CartItem) {

        cartDao.updateCartItem(cartItem)

    }

    /**
     * Delete item
     */
    suspend fun deleteCartItem(cartItem: CartItem) {

        cartDao.deleteCartItem(cartItem)

    }

    /**
     * Clear logged-in user's cart
     */
    suspend fun clearCart(userId: String) {

        cartDao.clearCart(userId)

    }

    /**
     * Check if product already exists for logged-in user
     */
    suspend fun getCartItemById(
        userId: String,
        productId: Int
    ): CartItem? {

        return cartDao.getCartItemById(
            userId,
            productId
        )

    }

}