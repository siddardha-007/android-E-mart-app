package com.example.ecommerce.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.data.repository.HomeRepository
import com.example.ecommerce.data.repository.ProductRepository
import com.example.ecommerce.model.Banner
import com.example.ecommerce.model.Category
import com.example.ecommerce.model.Product
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val repository = HomeRepository()


    private val _banners = MutableLiveData<List<Banner>>()
    val banners: LiveData<List<Banner>> = _banners

    // Categories
    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    // Products
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    // Loading
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    // Error
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    init {
        loadHomeData()
    }

    private fun loadHomeData() {

        _banners.value = repository.getBanners()

        getCategories()

        getAllProducts()
    }

    fun getCategories() {

        viewModelScope.launch {

            _loading.value = true

            try {

                val response = repository.getAllCategories()

                if (response.isSuccessful && response.body() != null) {

                    _categories.value = response.body()

                } else {

                    _error.value = "Unable to load categories"

                }

            } catch (e: Exception) {

                _error.value = e.localizedMessage

            }

            _loading.value = false
        }
    }

    fun getAllProducts() {

        viewModelScope.launch {

            _loading.value = true

            try {

                val response = repository.getAllProducts()

                if (response.isSuccessful && response.body() != null) {

                    _products.value = response.body()

                } else {

                    _error.value = "Unable to load products"

                }

            } catch (e: Exception) {

                _error.value = e.localizedMessage

            }

            _loading.value = false
        }
    }

    fun getProductsByCategory(categoryId: Int) {

        viewModelScope.launch {

            _loading.value = true

            try {

                val response = repository.getProductsByCategory(categoryId)

                if (response.isSuccessful && response.body() != null) {

                    _products.value = response.body()

                } else {

                    _error.value = "Unable to load category"

                }

            } catch (e: Exception) {

                _error.value = e.localizedMessage

            }

            _loading.value = false
        }
    }
}