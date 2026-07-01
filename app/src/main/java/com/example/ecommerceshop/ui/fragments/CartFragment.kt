package com.example.ecommerceshop.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.ecommerceshop.databinding.FragmentCartBinding
import com.example.ecommerceshop.ui.cart.CartItemsFragment
import com.example.ecommerceshop.ui.cart.DeliveryDetailsFragment
import com.google.android.material.tabs.TabLayoutMediator

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup the pager adapter with a callback to jump to the second tab
        val pagerAdapter = CartPagerAdapter(this) { navigateToTab(1) }
        binding.viewPager.adapter = pagerAdapter

        // Bind Figma tab indicators
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = if (position == 0) "Cart items" else "Delivery"
        }.attach()

        binding.btnBack.setOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }
    }

    private fun navigateToTab(index: Int) {
        binding.viewPager.currentItem = index
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private inner class CartPagerAdapter(
        fragment: Fragment,
        private val onNextStepRequested: () -> Unit
    ) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = 2
        override fun createFragment(position: Int): Fragment {
            return if (position == 0) {
                CartItemsFragment(onNextStepRequested)
            } else {
                DeliveryDetailsFragment()
            }
        }
    }
}