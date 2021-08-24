package com.example.practice

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practice.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private var dialog: AlertDialog? = null
    private val viewModel: RestaurantViewModel by viewModels()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var linearAdapter: RestaurantAdapter
    private lateinit var gridAdapter: GridRestaurantAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        linearLayoutManager = LinearLayoutManager(this.context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        gridLayoutManager = GridLayoutManager(this.context, 2)

        dialog = this.context.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton("ok") { dialog, _ ->
                    dialog.dismiss()
                }
                setTitle("Warning!")
                setMessage("Api is failure")
            }
            builder.create()
        }

        binding.mainToolbar.setOnMenuItemClickListener {
            Toast.makeText(this.context, "did Tap: ${it.itemId.toString()}, ${it.order.toString()}", Toast.LENGTH_SHORT).show()
            when(it.order) {
                0 -> {
                    binding.mainRecyclerView.adapter = linearAdapter
                    binding.mainRecyclerView.layoutManager = linearLayoutManager
                }
                1 -> {
                    binding.mainRecyclerView.adapter = gridAdapter
                    binding.mainRecyclerView.layoutManager = gridLayoutManager
                }
            }
            true
        }

        viewModel.restaurants.observe(viewLifecycleOwner, Observer {
            binding.mainProgressBar.isVisible = false
            linearAdapter = RestaurantAdapter(it.last().list.first())
            gridAdapter = GridRestaurantAdapter(it.last().list.first())
            binding.mainRecyclerView.adapter = linearAdapter
            binding.mainRecyclerView.layoutManager = linearLayoutManager
        })
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            binding.mainProgressBar.isVisible = false
            dialog?.show()
        })
        viewModel.getRestaurants()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}