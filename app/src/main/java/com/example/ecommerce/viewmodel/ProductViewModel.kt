package com.example.ecommerce.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.data.repository.ProductRepository
import com.example.ecommerce.model.Product
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {

    private val repository = ProductRepository()

    // Product List
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    // Loading State
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    // Error Message
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    /**
     * Fetch all products
     */
    fun getAllProducts() {

        viewModelScope.launch {

            _isLoading.value = true

            try {

                val response = repository.getAllProducts()

                if (response.isSuccessful && response.body() != null) {

                    _products.value = response.body()

                } else {

                    _error.value = "Failed to load products"

                }

            } catch (e: Exception) {

                _error.value = e.localizedMessage ?: "Something went wrong"

            } finally {

                _isLoading.value = false

            }
        }
    }

    /**
     * Fetch a single product by ID
     */
    fun getProductById(
        id: Int,
        onSuccess: (Product) -> Unit,
        onFailure: (String) -> Unit
    ) {

        viewModelScope.launch {

            try {

                val response = repository.getProductById(id)

                if (response.isSuccessful && response.body() != null) {

                    onSuccess(response.body()!!)

                } else {

                    onFailure("Product not found")

                }

            } catch (e: Exception) {

                onFailure(e.localizedMessage ?: "Something went wrong")

            }
        }
    }
}