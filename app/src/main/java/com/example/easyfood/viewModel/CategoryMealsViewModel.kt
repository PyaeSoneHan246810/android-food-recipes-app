package com.example.easyfood.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easyfood.pojo.MealByCategory
import com.example.easyfood.pojo.MealByCategoryList
import com.example.easyfood.retrofit.MealApiClient
import retrofit2.Call
import retrofit2.Response

class CategoryMealsViewModel: ViewModel() {
    //Retrofit Live Data
    private var categoryMealsLiveData = MutableLiveData<List<MealByCategory>>()

    //Observe Retrofit Live Data
    fun observeCategoryMealsLiveData(): LiveData<List<MealByCategory>> = categoryMealsLiveData

    //Retrofit Methods
    fun getMealsByCategory(categoryName: String){
        val mealsByCategoryApiCall = MealApiClient.mealApiService.getMealsByCategory(categoryName)
        mealsByCategoryApiCall.enqueue(object : retrofit2.Callback<MealByCategoryList>{
            override fun onResponse(
                call: Call<MealByCategoryList>,
                response: Response<MealByCategoryList>
            ) {
                if (!response.isSuccessful) {
                    Log.d("MealsByCategoryApiCall", "onResponse Error: ${response.errorBody().toString()}")
                    return
                }
                if (response.body() == null) {
                    Log.d("MealsByCategoryApiCall", "onResponse Error: null response body")
                    return
                }
                val mealsByCategory: List<MealByCategory>? = response.body()?.meals
                categoryMealsLiveData.value = mealsByCategory!!
            }

            override fun onFailure(call: Call<MealByCategoryList>, t: Throwable) {
                Log.d("MealsByCategoryApiCall", "onFailure: ${t.message}")
            }

        })
    }
}