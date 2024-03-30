package com.example.easyfood.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.easyfood.pojo.Meal
import com.example.easyfood.pojo.MealList
import com.example.easyfood.repository.FavoriteMealRepository
import com.example.easyfood.retrofit.MealApiClient
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class MealViewModel(
    private val favoriteMealRepository: FavoriteMealRepository
): ViewModel() {
    //Retrofit Live Data
    private var mealDetailsLiveData = MutableLiveData<Meal>()

    //Observe Retrofit Live Data
    fun observeMealDetailsLiveData(): LiveData<Meal> = mealDetailsLiveData

    //Retrofit Methods
    fun getMealDetailsById(id: String) {
        val mealDetailsApiCall = MealApiClient.mealApiService.getMealDetailsById(id = id)
        mealDetailsApiCall.enqueue(object : retrofit2.Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (!response.isSuccessful) {
                    Log.d("MealDetailsApiCall", "onResponse Error: ${response.errorBody().toString()}")
                    return
                }
                if (response.body() == null) {
                    Log.d("MealDetailsApiCall", "onResponse Error: null response body")
                    return
                }
                val meals: List<Meal>? = response.body()?.meals
                val meal: Meal = meals!!.first()
                mealDetailsLiveData.value = meal
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("MealDetailsApiCall", "onFailure: ${t.message}")
            }

        })
    }

    //ROOM Database Methods
    fun addMealToFavorites(meal: Meal) {
        viewModelScope.launch {
            favoriteMealRepository.addMealToFavorites(meal)
        }
    }
}