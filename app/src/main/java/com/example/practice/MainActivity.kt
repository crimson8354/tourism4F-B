package com.example.practice

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practice.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var dialog: AlertDialog? = null

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

        val service = ApiManager.client.create(ApiService::class.java)
        val job = CoroutineScope(Dispatchers.IO).launch {
            Log.i("test", (Looper.myLooper() == Looper.getMainLooper()).toString())
            service.data().enqueue(object: Callback<Welcome> {
                override fun onResponse(
                    call: Call<Welcome>,
                    response: Response<Welcome>
                ) {
                    Log.i("test", (Looper.myLooper() == Looper.getMainLooper()).toString())
                    if (response.isSuccessful) {
                        binding.mainProgressBar.isVisible = false
                        val data: List<Restaurant>? = response.body()?.xmlHead?.infos?.info
                        if (data != null) {
                            recyclerView.adapter = RestaurantAdapter(data)
                        }
                    }
                }

                override fun onFailure(call: Call<Welcome>, t: Throwable) {
                    Log.i("test","api is failure, ${call.isCanceled}, ${t.localizedMessage}")
                    binding.mainProgressBar.isVisible = false
                    dialog?.show()
                }
            })
        }
    }
}