package com.example.practice.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practice.adapter.GridRestaurantAdapter
import com.example.practice.adapter.RestaurantAdapter
import com.example.practice.databinding.FragmentMainBinding
import com.example.practice.model.Restaurant
import com.example.practice.viewmodels.RestaurantRegion
import com.example.practice.viewmodels.RestaurantTown
import com.example.practice.viewmodels.RestaurantViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment() : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RestaurantViewModel by activityViewModels()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var linearAdapter: RestaurantAdapter
    private lateinit var gridAdapter: GridRestaurantAdapter
    private val data: RestaurantTown = RestaurantTown("All", emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            val region: RestaurantRegion = it.getParcelable(REGION)!!
            for (town in region.list) {
                val header = Restaurant(id = "0", town = town.list.first().town)
                data.list += listOf(header) + town.list
            }
        }

        linearLayoutManager = LinearLayoutManager(this.context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        gridLayoutManager = GridLayoutManager(this.context, 2)
        gridLayoutManager.spanSizeLookup = object: GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when(data.list[position].id) {
                    "0" -> 2
                    else -> 1
                }
            }
        }
        linearAdapter = RestaurantAdapter(data)
        gridAdapter = GridRestaurantAdapter(data)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mainRecyclerView.layoutManager = linearLayoutManager
        binding.mainRecyclerView.adapter = linearAdapter

        viewModel.mode.observe(viewLifecycleOwner, Observer {
            when(it) {
                RestaurantViewModel.Mode.GRID -> {
                    binding.mainRecyclerView.layoutManager = gridLayoutManager
                    binding.mainRecyclerView.adapter = gridAdapter
                }
                RestaurantViewModel.Mode.LINEAR -> {
                    binding.mainRecyclerView.layoutManager = linearLayoutManager
                    binding.mainRecyclerView.adapter = linearAdapter
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    companion object {
        private const val REGION = "restaurant_region"
        fun newInstance(region: RestaurantRegion) = MainFragment().apply {
            arguments = bundleOf(REGION to region)
        }
    }
}