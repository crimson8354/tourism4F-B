package com.example.practice.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.practice.ui.Coordinate
import com.example.practice.databinding.RestaurantGridLayoutBinding
import com.example.practice.databinding.RestaurantHeaderLayoutBinding
import com.example.practice.model.Restaurant
import com.example.practice.ui.RootFragmentDirections
import com.example.practice.viewmodels.RestaurantRegion
import com.example.practice.viewmodels.RestaurantTown

private const val RESTAURANT_HEADER = 0
private const val RESTAURANT_CELL = 1

class GridRestaurantAdapter(private val data: RestaurantTown): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return if (data.list[position].id == RESTAURANT_HEADER.toString()) RESTAURANT_HEADER else RESTAURANT_CELL
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var inflater = LayoutInflater.from(parent.context)
        if (viewType == RESTAURANT_HEADER) {
            val binding = RestaurantHeaderLayoutBinding.inflate(inflater, parent, false)
            return HeaderViewHolder(binding)
        }
        val binding = RestaurantGridLayoutBinding.inflate(inflater, parent, false)
        return CellViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val info = data.list[position]
        when(holder) {
            is HeaderViewHolder -> {
                holder.binding.titleTextView.text = info.town
            }
            is CellViewHolder -> {
                Glide.with(holder.binding.root).load(info.picture1).placeholder(android.R.drawable.gallery_thumb).into(holder.binding.gridMainImageView)
                holder.binding.gridNameTextView.text = info.name
                holder.binding.root.setOnClickListener {
                    val action = RootFragmentDirections.actionRootFragmentToDetailFragment(
                        info.address,
                        Coordinate(info.longitude, info.latitude)
                    )
                    it.findNavController().navigate(action)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return data.list.count()
    }

    inner class CellViewHolder(val binding: RestaurantGridLayoutBinding): RecyclerView.ViewHolder(binding.root) {}
    inner class HeaderViewHolder(val binding: RestaurantHeaderLayoutBinding) : RecyclerView.ViewHolder(binding.root) {}
}