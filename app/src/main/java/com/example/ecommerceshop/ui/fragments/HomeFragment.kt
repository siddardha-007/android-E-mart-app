package com.example.ecommerceshop.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerceshop.adapter.BannerAdapter
import com.example.ecommerceshop.adapter.CategoryAdapter
import com.example.ecommerceshop.adapter.ProductAdapter
import com.example.ecommerceshop.databinding.FragmentHomeBinding
import com.example.ecommerceshop.viewmodel.HomeViewModel
import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.ecommerceshop.utils.LocationHelper

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private lateinit var locationHelper: LocationHelper
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()

    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var featuredAdapter: ProductAdapter
    private lateinit var bestDealsAdapter: ProductAdapter
    private lateinit var bannerAdapter: BannerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        locationHelper = LocationHelper(requireContext())

        setupRecyclerViews()

        observeViewModel()

        checkLocationPermission()

        return binding.root
    }

    private fun checkLocationPermission() {

        if (

            ContextCompat.checkSelfPermission(

                requireContext(),

                Manifest.permission.ACCESS_FINE_LOCATION

            ) == PackageManager.PERMISSION_GRANTED

        ) {

            getCurrentLocation()

        } else {

            locationPermissionLauncher.launch(
                Manifest.permission.ACCESS_FINE_LOCATION
            )

        }

    }

    private fun getCurrentLocation() {

        locationHelper.getCurrentLocation(

            object : LocationHelper.LocationCallback {

                override fun onLocationReceived(location: String) {

                    binding.tvLocation.text = location

                }

                override fun onLocationFailed(message: String) {

                    binding.tvLocation.text = "Unknown"

                }

            }

        )

    }

    private fun setupRecyclerViews() {

        bannerAdapter = BannerAdapter(emptyList())

        binding.viewPagerBanner.adapter = bannerAdapter

        categoryAdapter = CategoryAdapter(emptyList()) {

            homeViewModel.getProductsByCategory(it.id)

        }

        binding.rvCategories.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        binding.rvCategories.adapter = categoryAdapter


        featuredAdapter = ProductAdapter(emptyList()) {

            Toast.makeText(
                requireContext(),
                it.title,
                Toast.LENGTH_SHORT
            ).show()

        }

        binding.rvBestDeals.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        binding.rvBestDeals.adapter = featuredAdapter


        bestDealsAdapter = ProductAdapter(emptyList()) {

            Toast.makeText(
                requireContext(),
                it.title,
                Toast.LENGTH_SHORT
            ).show()

        }

        binding.rvBestDeals.layoutManager =
            GridLayoutManager(requireContext(), 2)

        binding.rvBestDeals.adapter = bestDealsAdapter

    }

    private fun observeViewModel() {

        homeViewModel.banners.observe(viewLifecycleOwner) {

            bannerAdapter.updateBanners(it)

        }

        homeViewModel.categories.observe(viewLifecycleOwner) {

            categoryAdapter.updateCategories(it)

        }

        homeViewModel.products.observe(viewLifecycleOwner) {

            featuredAdapter.updateProducts(it)

            bestDealsAdapter.updateProducts(it)

        }

        homeViewModel.error.observe(viewLifecycleOwner) {

            Toast.makeText(
                requireContext(),
                it,
                Toast.LENGTH_SHORT
            ).show()

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private val locationPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->

            if (isGranted) {

                getCurrentLocation()

            } else {

                binding.tvLocation.text = "Location Denied"

            }

        }

}