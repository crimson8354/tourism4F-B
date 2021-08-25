package com.example.practice

import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

@HiltViewModel
class RestaurantViewModel @Inject constructor(private val repository: RestaurantRepository): ViewModel() {
    enum class Mode {
        LINEAR, GRID
    }
    val restaurants = MutableLiveData<List<RestaurantRegion>>()
    val errorMessage = MutableLiveData<String>()
    val mode = MutableLiveData<Mode>()

    init {
        mode.postValue(Mode.LINEAR)
    }

    fun getRestaurants() {
        viewModelScope.launch(Dispatchers.IO) {
            val restaurants = repository.getRestaurants()
            if (restaurants != null) {
                this@RestaurantViewModel.calculate(restaurants)
            } else {
                errorMessage.postValue("Api is failure")
            }
        }
    }

    private fun calculate(restaurants: List<Restaurant>) {
        if (restaurants.isEmpty()) {
            return
        }

        var result: List<RestaurantRegion> = emptyList()
        for (restaurant in restaurants) {
            if (restaurant.region == null) {
                restaurant.region = restaurant.address.substring(0..2).trim()
                Log.i("test", restaurant.region)
            }
            if (restaurant.town == null) {
                restaurant.town = restaurant.address.substring(3..5).trim()
                Log.i("test", restaurant.town)
            }

            val region = result.find { it.name == restaurant.region }
            if (region != null) {
                var isFound = false
                for (town in region.list) {
                    if (town.name == restaurant.town) {
                        town.list += restaurant
                        isFound = true
                        break
                    }
                }
                if (!isFound) {
                    region.list += RestaurantTown(restaurant.town, listOf(restaurant))
                }
            } else {
                result += RestaurantRegion(restaurant.region, listOf(RestaurantTown(restaurant.town, listOf(restaurant))))
            }
        }
        this.restaurants.postValue(result)
    }

    fun toggleMode(index: Int) {
        if (mode.value?.ordinal == index) {
            return
        }
        mode.postValue(Mode.values()[index])
    }
}

@Parcelize
data class RestaurantRegion(val name: String, var list: List<RestaurantTown>): Parcelable
@Parcelize
data class RestaurantTown(val name: String, var list: List<Restaurant>): Parcelable