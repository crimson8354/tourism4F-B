package com.example.practice

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
import com.example.practice.databinding.FragmentMainBinding
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
    private lateinit var region: RestaurantRegion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            region = it.getParcelable(MainFragment.REGION)!!
        }
        linearLayoutManager = LinearLayoutManager(this.context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        gridLayoutManager = GridLayoutManager(this.context, 2)
        linearAdapter = RestaurantAdapter(region.list.first()) // TODO - need to display all data
        gridAdapter = GridRestaurantAdapter(region.list.first()) // TODO - need to display all data
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
            arguments = bundleOf(MainFragment.REGION to region)
        }
    }
}