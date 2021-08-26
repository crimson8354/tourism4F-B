package com.example.practice.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.practice.ui.MainFragment
import com.example.practice.viewmodels.RestaurantRegion

class MainPagerAdapter(private val regions: List<RestaurantRegion>, fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return regions.count()
    }

    override fun createFragment(position: Int): Fragment {
        return MainFragment.newInstance(regions[position])
    }
}