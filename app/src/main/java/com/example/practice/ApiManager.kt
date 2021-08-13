package com.example.practice

import android.util.Log
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiManager private constructor() {
    private val retrofit: Retrofit
    private val url: String = "https://gis.taiwan.net.tw/"
    private var logging = HttpLoggingInterceptor(object: HttpLoggingInterceptor.Logger {
        override fun log(message: String) {
            Log.i("test", message)
        }
    })
    private var okHttpClient: OkHttpClient

    init {
        logging.level = HttpLoggingInterceptor.Level.BASIC
        okHttpClient = OkHttpClient().newBuilder()
                        .addInterceptor(logging)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .writeTimeout(60, TimeUnit.SECONDS)
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .build()
        retrofit = Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build()
    }

    companion object {
        private val manager = ApiManager()
        val client: Retrofit
            get() = manager.retrofit
    }
}