package com.example.ecommerceshop.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerceshop.R
import com.example.ecommerceshop.adapter.CategoryChipAdapter
import com.example.ecommerceshop.adapter.ProductGridAdapter
import com.example.ecommerceshop.databinding.FragmentProductsBinding
import com.example.ecommerceshop.model.Category
import com.example.ecommerceshop.ui.products.ProductDetailsActivity
import com.example.ecommerceshop.viewmodel.CartViewModel
import com.example.ecommerceshop.viewmodel.HomeViewModel

class ProductsFragment : Fragment() {

    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()
    private val cartViewModel: CartViewModel by viewModels()

    private lateinit var productAdapter: ProductGridAdapter
    private lateinit var categoryAdapter: CategoryChipAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductsBinding.inflate(inflater, container, false)

        setupRecyclerViews()
        observeViewModel()
        setupSwipeRefresh()
        loadData()

        // Set up manual action to return or pop fragment back stack
        binding.btnBack.setOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }

        return binding.root
    }

    private fun setupRecyclerViews() {
        productAdapter = ProductGridAdapter(
            products = emptyList(),
            onProductClick = { product ->
                val intent = Intent(requireContext(), ProductDetailsActivity::class.java)
                intent.putExtra("product", product)
                startActivity(intent)
            },
            onAddToCartClick = { cartItem ->
                cartViewModel.addToCart(cartItem)
                Toast.makeText(requireContext(), "Added to Cart", Toast.LENGTH_SHORT).show()
            },
            onWishlistClick = { product ->
                Toast.makeText(requireContext(), "${product.title} added to Wishlist", Toast.LENGTH_SHORT).show()
            }
        )

        binding.rvProducts.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvProducts.adapter = productAdapter

        // FIXED: Using CategoryChipAdapter instead of CategoryAdapter
        categoryAdapter = CategoryChipAdapter(emptyList()) { category ->
            if (category.id == -1) {
                homeViewModel.getAllProducts()
            } else {
                homeViewModel.getProductsByCategory(category.id)
            }
        }

        binding.rvCategoryFilter.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.rvCategoryFilter.adapter = categoryAdapter
    }

    private fun observeViewModel() {
        homeViewModel.products.observe(viewLifecycleOwner) { products ->
            binding.progressBar.visibility = View.GONE
            binding.swipeRefresh.isRefreshing = false
            productAdapter.updateProducts(products)
            binding.layoutEmpty.visibility = if (products.isEmpty()) View.VISIBLE else View.GONE
        }

        homeViewModel.categories.observe(viewLifecycleOwner) { apiCategories ->
            val categories = mutableListOf<Category>()
            categories.add(Category(id = -1, name = "All", image = ""))
            categories.addAll(apiCategories)

            // FIXED: Calls updateData() defined inside your CategoryChipAdapter
            categoryAdapter.updateData(categories)
        }

        homeViewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        homeViewModel.error.observe(viewLifecycleOwner) { errorMsg ->
            if (!errorMsg.isNullOrBlank()) {
                Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener { loadData() }
    }

    private fun loadData() {
        homeViewModel.getCategories()
        homeViewModel.getAllProducts()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}