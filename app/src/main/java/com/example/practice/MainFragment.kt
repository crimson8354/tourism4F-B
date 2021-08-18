package com.example.practice

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        val layout = LinearLayoutManager(this.context)
        layout.orientation = LinearLayoutManager.VERTICAL
        val recyclerView = binding.mainRecyclerView
        recyclerView.layoutManager = layout
        var adapter =  RestaurantAdapter(emptyList())
        adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        recyclerView.adapter = adapter

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

        viewModel.restaurants.observe(viewLifecycleOwner, Observer {
            binding.mainProgressBar.isVisible = false
            recyclerView.adapter = RestaurantAdapter(it)
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

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}