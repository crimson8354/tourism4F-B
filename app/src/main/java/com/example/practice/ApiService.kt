package com.example.practice

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("/XMLReleaseALL_public/restaurant_C_f.json")
    fun data(): Call<Welcome>
}