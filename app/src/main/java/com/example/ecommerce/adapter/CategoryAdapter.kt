package com.example.ecommerce.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.R
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerce.databinding.ItemCategoryBinding
import com.example.ecommerce.model.Category

class CategoryAdapter(
    private var categories: List<Category>,
    private val onCategoryClick: (Category) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var selectedPosition = 0

    inner class CategoryViewHolder(
        private val binding: ItemCategoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(category: Category, position: Int) {

            binding.tvCategoryName.text = category.name

            if (category.id == -1) {

                binding.ivCategory.setImageResource(com.example.ecommerce.R.drawable.ic_categories)

            } else {

                Glide.with(binding.root.context)
                    .load(category.image)
                    .placeholder(com.example.ecommerce.R.drawable.ic_categories)
                    .error(com.example.ecommerce.R.drawable.ic_categories)
                    .circleCrop()
                    .into(binding.ivCategory)

            }

            if (selectedPosition == position) {

                binding.root.strokeWidth = 4
                binding.root.strokeColor = 0xFF4CAF50.toInt()

            } else {

                binding.root.strokeWidth = 0

            }

            binding.root.setOnClickListener {

                val previousPosition = selectedPosition

                selectedPosition = adapterPosition

                notifyItemChanged(previousPosition)
                notifyItemChanged(selectedPosition)

                onCategoryClick(category)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryViewHolder {

        val binding = ItemCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CategoryViewHolder,
        position: Int
    ) {

        holder.bind(categories[position], position)

    }

    override fun getItemCount(): Int = categories.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateCategories(newCategories: List<Category>) {

        categories = newCategories
        selectedPosition = 0
        notifyDataSetChanged()

    }
}