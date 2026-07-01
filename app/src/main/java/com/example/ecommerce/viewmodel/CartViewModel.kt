package com.example.ecommerce.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.data.local.CartDatabase
import com.example.ecommerce.data.repository.CartRepository
import com.example.ecommerce.model.CartItem
import kotlinx.coroutines.launch

class CartViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: CartRepository

    val cartItems: LiveData<List<CartItem>>

    val cartItemCount: LiveData<Int>

    init {

        val cartDao = CartDatabase
            .getDatabase(application)
            .cartDao()

        repository = CartRepository(cartDao)

        cartItems = repository.allCartItems

        cartItemCount = repository.cartItemCount
    }

    /**
     * Add product to cart
     */
    fun addToCart(cartItem: CartItem) {

        viewModelScope.launch {

            val existingItem =
                repository.getCartItemById(cartItem.productId)

            if (existingItem != null) {

                repository.updateCartItem(
                    existingItem.copy(
                        quantity = existingItem.quantity + 1
                    )
                )

            } else {

                repository.insertCartItem(cartItem)

            }
        }
    }

    /**
     * Update quantity
     */
    fun updateQuantity(
        cartItem: CartItem,
        quantity: Int
    ) {

        viewModelScope.launch {

            if (quantity <= 0) {

                repository.deleteCartItem(cartItem)

            } else {

                repository.updateCartItem(
                    cartItem.copy(quantity = quantity)
                )

            }

        }
    }

    /**
     * Increase quantity
     */
    fun increaseQuantity(cartItem: CartItem) {

        updateQuantity(
            cartItem,
            cartItem.quantity + 1
        )

    }

    /**
     * Decrease quantity
     */
    fun decreaseQuantity(cartItem: CartItem) {

        updateQuantity(
            cartItem,
            cartItem.quantity - 1
        )

    }

    /**
     * Remove single item
     */
    fun removeItem(cartItem: CartItem) {

        viewModelScope.launch {

            repository.deleteCartItem(cartItem)

        }

    }

    /**
     * Clear cart
     */
    fun clearCart() {

        viewModelScope.launch {

            repository.clearCart()

        }

    }

    /**
     * Calculate total price
     */
    fun calculateTotal(items: List<CartItem>): Double {

        return items.sumOf {

            it.price * it.quantity

        }

    }
}