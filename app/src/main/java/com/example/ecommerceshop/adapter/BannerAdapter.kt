package com.example.ecommerceshop.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerceshop.databinding.ItemBannerBinding
import com.example.ecommerceshop.model.Banner

class BannerAdapter(
    private var banners: List<Banner>,
    private val onBannerClick: (Banner) -> Unit = {}
) : RecyclerView.Adapter<BannerAdapter.BannerViewHolder>() {

    inner class BannerViewHolder(
        private val binding: ItemBannerBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(banner: Banner) {
            // Bind the main title ("Pay Later !")
            binding.tvBannerTitle.text = banner.title

            // Bind the subtitle/description ("Shop now") safely
            binding.tvBannerSubtitle.text = banner.description

            // Bind the dynamic text for the pill badge ("OCTOBER 7")
            // If your model doesn't have a badge field yet, you can use banner.badge text
            // Option 2A: Directly assigning a string literal
            binding.tvBannerBadge.text = "OCTOBER 7"


            // Load the contextual product layout background illustration smoothly via Glide
            Glide.with(binding.root.context)
                .load(banner.imageUrl)
                .centerCrop()
                .into(binding.ivBanner)

            binding.root.setOnClickListener {
                onBannerClick(banner)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BannerViewHolder {
        val binding = ItemBannerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BannerViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: BannerViewHolder,
        position: Int
    ) {
        holder.bind(banners[position])
    }

    override fun getItemCount(): Int = banners.size

    fun updateBanners(newBanners: List<Banner>) {
        banners = newBanners
        notifyDataSetChanged()
    }
}