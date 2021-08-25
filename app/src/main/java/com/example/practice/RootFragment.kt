package com.example.practice

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.practice.databinding.FragmentRootBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RootFragment : Fragment() {
    private var _binding: FragmentRootBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RestaurantViewModel by activityViewModels()
    private var dialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getRestaurants()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRootBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mainTabBar.isVisible = false

        binding.mainToolbar.setOnMenuItemClickListener {
            Toast.makeText(this.context, "did Tap: ${it.itemId.toString()}, ${it.order.toString()}", Toast.LENGTH_SHORT).show()
            viewModel.toggleMode(it.order)
            true
        }

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
            val adapter = MainPagerAdapter(it, requireActivity().supportFragmentManager, lifecycle)
            binding.mainViewPager.adapter = adapter
            TabLayoutMediator(binding.mainTabBar, binding.mainViewPager) { tab: TabLayout.Tab, i: Int ->
                tab.text = it[i].name
            }.attach()
            binding.mainTabBar.isVisible = true
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            binding.mainProgressBar.isVisible = false
            dialog?.show()
        })
    }
}