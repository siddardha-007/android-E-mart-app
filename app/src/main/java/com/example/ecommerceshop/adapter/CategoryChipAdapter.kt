package com.example.ecommerceshop.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceshop.databinding.ItemCategoryChipBinding
import com.example.ecommerceshop.model.Category

class CategoryChipAdapter(
    private var categories: List<Category>,
    private val onCategoryClick: (Category) -> Unit
) : RecyclerView.Adapter<CategoryChipAdapter.ViewHolder>() {

    private var selectedPosition = 0

    inner class ViewHolder(
        private val binding: ItemCategoryChipBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(category: Category, position: Int) {
            binding.tvCategory.text = category.name

            if (position == selectedPosition) {
                // Active configuration matching Figma design profile
                binding.tvCategory.setTextColor(Color.parseColor("#1A6E8A"))
                binding.indicator.visibility = View.VISIBLE
            } else {
                // Default unselected state profile
                binding.tvCategory.setTextColor(Color.parseColor("#9E9E9E"))
                binding.indicator.visibility = View.INVISIBLE
            }

            binding.root.setOnClickListener {
                val currentPos = adapterPosition
                if (currentPos == RecyclerView.NO_POSITION) return@setOnClickListener

                val previous = selectedPosition
                selectedPosition = currentPos

                notifyItemChanged(previous)
                notifyItemChanged(selectedPosition)

                onCategoryClick(category)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCategoryChipBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(categories[position], position)
    }

    override fun getItemCount() = categories.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newList: List<Category>) {
        this.categories = newList
        notifyDataSetChanged()
    }
}