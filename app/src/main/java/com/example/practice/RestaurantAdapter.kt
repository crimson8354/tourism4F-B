package com.example.practice

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.practice.databinding.RestaurantLayoutBinding

class RestaurantAdapter(private val data: List<Restaurant>): RecyclerView.Adapter<RestaurantAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var inflater = LayoutInflater.from(parent.context)
        val binding = RestaurantLayoutBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = data[position]
        holder.binding.nameTextView.text = info.name
        holder.binding.openTimeTextView.text = info.openTime
        holder.binding.telephoneTextView.text = info.telephone
        holder.binding.descTextView.text = info.desc
        holder.binding.mainImageView.contentDescription = info.picDesc1
        holder.binding.addressTextView.text = info.address
        Glide.with(holder.binding.root).load(info.picture1).placeholder(android.R.drawable.gallery_thumb).into(holder.binding.mainImageView)
    }

    override fun getItemCount(): Int {
        return data.count()
    }

    inner class ViewHolder(val binding: RestaurantLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
    }
}