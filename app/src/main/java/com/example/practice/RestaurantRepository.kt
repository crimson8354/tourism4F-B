package com.example.practice

import android.os.Looper
import android.util.Log
import androidx.core.view.isVisible
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class RestaurantRepository @Inject constructor(private val service: ApiService) {
    fun getRestaurants(result: TaskResult<List<Restaurant>>) {
        CoroutineScope(Dispatchers.IO).launch {
            Log.i("test", (Looper.myLooper() == Looper.getMainLooper()).toString())
            service.data().enqueue(object: Callback<Welcome> {
                override fun onResponse(
                    call: Call<Welcome>,
                    response: Response<Welcome>
                ) {
                    Log.i("test", (Looper.myLooper() == Looper.getMainLooper()).toString())
                    if (response.isSuccessful) {
                        val data: List<Restaurant>? = response.body()?.xmlHead?.infos?.info
                        if (data != null) {
                            result.onSuccess(data)
                            return
                        }
                    }
                    result.onFailure("api is failure")
                }

                override fun onFailure(call: Call<Welcome>, t: Throwable) {
                    Log.i("test","api is failure, ${call.isCanceled}, ${t.localizedMessage}")
                    result.onFailure("api is failure")
                }
            })
        }
    }
}