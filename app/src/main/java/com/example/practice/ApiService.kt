package com.example.practice

import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    companion object {
        const val url: String = "https://gis.taiwan.net.tw/"
    }
    @GET("/XMLReleaseALL_public/restaurant_C_f.json")
    suspend fun data(): Response<Welcome>
}