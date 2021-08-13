package com.example.practice

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RestaurantViewModel @Inject constructor(val repository: RestaurantRepository): ViewModel() {
    val restaurants = MutableLiveData<List<Restaurant>>()
    val errorMessage = MutableLiveData<String>()

    fun getRestaurants() {
        repository.getRestaurants(object: TaskResult<List<Restaurant>> {
            override fun onSuccess(data: List<Restaurant>) {
                restaurants.postValue(data)
            }

            override fun onFailure(message: String) {
                errorMessage.postValue(message)
            }
        })
    }
}