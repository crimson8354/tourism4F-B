package com.example.practice.repository

import com.example.practice.model.Restaurant
import com.example.practice.service.ApiService
import javax.inject.Inject

class RestaurantRepository @Inject constructor(private val service: ApiService) {
    suspend fun getRestaurants(): List<Restaurant>? {
        val response = service.data()
        if (response.isSuccessful) {
            val data: List<Restaurant>? = response.body()?.xmlHead?.infos?.info
            if (data != null) {
                return data
            }
        }
        return null
    }
}