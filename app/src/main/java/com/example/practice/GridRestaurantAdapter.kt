package com.example.practice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.practice.databinding.RestaurantGridLayoutBinding

class GridRestaurantAdapter(private val data: RestaurantTown): RecyclerView.Adapter<GridRestaurantAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var inflater = LayoutInflater.from(parent.context)
        val binding = RestaurantGridLayoutBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = data.list[position]
        Glide.with(holder.binding.root).load(info.picture1).into(holder.binding.gridMainImageView)
        holder.binding.gridNameTextView.text = info.name
        holder.binding.root.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToDetailFragment(info.address, Coordinate(info.longitude, info.latitude))
            it.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return data.list.count()
    }

    inner class ViewHolder(val binding: RestaurantGridLayoutBinding): RecyclerView.ViewHolder(binding.root) {}
}