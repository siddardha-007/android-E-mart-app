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

            binding.tvBannerTitle.text = banner.title

            binding.tvBannerDescription.text = banner.description

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