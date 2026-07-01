package com.example.ecommerce.data.repository

import androidx.lifecycle.LiveData
import com.example.ecommerce.data.local.CartDao
import com.example.ecommerce.model.CartItem

class CartRepository(
    private val cartDao: CartDao
) {

    // Get all cart items
    val allCartItems: LiveData<List<CartItem>> =
        cartDao.getCartItems()

    // Get cart count
    val cartItemCount: LiveData<Int> =
        cartDao.getCartItemCount()

    // Insert product into cart
    suspend fun insertCartItem(cartItem: CartItem) {
        cartDao.insertCartItem(cartItem)
    }

    // Update cart item
    suspend fun updateCartItem(cartItem: CartItem) {
        cartDao.updateCartItem(cartItem)
    }

    // Delete cart item
    suspend fun deleteCartItem(cartItem: CartItem) {
        cartDao.deleteCartItem(cartItem)
    }

    // Clear entire cart
    suspend fun clearCart() {
        cartDao.clearCart()
    }

    // Check if product already exists
    suspend fun getCartItemById(productId: Int): CartItem? {
        return cartDao.getCartItemById(productId)
    }
}