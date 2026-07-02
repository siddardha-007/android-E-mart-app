package com.example.ecommerceshop.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.ecommerceshop.data.local.CartDatabase
import com.example.ecommerceshop.data.repository.CartRepository
import com.example.ecommerceshop.model.CartItem
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class CartViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: CartRepository

    private val userId: String =
        FirebaseAuth.getInstance().currentUser?.uid ?: ""

    val cartItems: LiveData<List<CartItem>>

    val cartItemCount: LiveData<Int>

    init {

        val cartDao = CartDatabase
            .getDatabase(application)
            .cartDao()

        repository = CartRepository(cartDao)

        cartItems = repository.getCartItems(userId)

        cartItemCount = repository.getCartItemCount(userId)

    }

    /**
     * Add product to cart
     */
    fun addToCart(cartItem: CartItem) {

        viewModelScope.launch {

            val existingItem =
                repository.getCartItemById(
                    userId,
                    cartItem.productId
                )

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
                    cartItem.copy(
                        quantity = quantity
                    )
                )

            }

        }

    }

    fun increaseQuantity(cartItem: CartItem) {

        updateQuantity(
            cartItem,
            cartItem.quantity + 1
        )

    }

    fun decreaseQuantity(cartItem: CartItem) {

        updateQuantity(
            cartItem,
            cartItem.quantity - 1
        )

    }

    fun removeItem(cartItem: CartItem) {

        viewModelScope.launch {

            repository.deleteCartItem(cartItem)

        }

    }

    /**
     * Clear current user's cart
     */
    fun clearCart() {

        viewModelScope.launch {

            repository.clearCart(userId)

        }

    }

    /**
     * Total price
     */
    fun calculateTotal(
        items: List<CartItem>
    ): Double {

        return items.sumOf {

            it.price * it.quantity

        }

    }

}