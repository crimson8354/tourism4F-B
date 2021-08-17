package com.example.practice

import android.os.Looper
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantViewModel @Inject constructor(private val repository: RestaurantRepository): ViewModel() {
    val restaurants = MutableLiveData<List<Restaurant>>()
    val errorMessage = MutableLiveData<String>()

    fun getRestaurants() {
        viewModelScope.launch(Dispatchers.IO) {
            val restaurants = repository.getRestaurants()
            if (restaurants != null) {
                this@RestaurantViewModel.restaurants.postValue(restaurants)
            } else {
                errorMessage.postValue("Api is failure")
            }
        }
    }
}