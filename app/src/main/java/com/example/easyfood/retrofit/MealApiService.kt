package com.example.easyfood.retrofit

import com.example.easyfood.pojo.CategoryList
import com.example.easyfood.pojo.MealList
import com.example.easyfood.pojo.MealByCategoryList
import com.example.easyfood.pojo.PopularMealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApiService {
    @GET("random.php")
    fun getRandomMeal(): Call<MealList>

    @GET("lookup.php")
    fun getMealDetailsById(@Query("i") id: String): Call<MealList>

    @GET("filter.php")
    fun getPopularMeals(@Query("c") categoryName: String): Call<PopularMealList>

    @GET("categories.php")
    fun getCategories(): Call<CategoryList>

    @GET("filter.php")
    fun getMealsByCategory(@Query("c") categoryName: String): Call<MealByCategoryList>

    @GET("search.php")
    fun searchMeals(@Query("s") searchQuery: String): Call<MealList>
}