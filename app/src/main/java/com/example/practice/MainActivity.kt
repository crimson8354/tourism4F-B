package com.example.practice

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practice.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var dialog: AlertDialog? = null
    private val viewModel: RestaurantViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layout = LinearLayoutManager(this)
        layout.orientation = LinearLayoutManager.VERTICAL
        val recyclerView = binding.mainRecyclerView
        recyclerView.layoutManager = layout
        
        dialog = this.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton("ok", DialogInterface.OnClickListener { dialog, _ ->
                    dialog.dismiss()
                })
                setTitle("Warning!")
                setMessage("Api is failure")
            }
            builder.create()
        }

        viewModel.restaurants.observe(this, Observer {
            Log.i("test", "${(Looper.myLooper() == Looper.getMainLooper()).toString()} in live data observe")
            binding.mainProgressBar.isVisible = false
            recyclerView.adapter = RestaurantAdapter(it)
        })

        viewModel.errorMessage.observe(this, Observer {
            binding.mainProgressBar.isVisible = false
            dialog?.show()
        })
    }

    override fun onStart() {
        super.onStart()

        viewModel.getRestaurants()
    }
}