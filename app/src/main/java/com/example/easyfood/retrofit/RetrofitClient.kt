package com.example.easyfood.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"
    val retrofit: Retrofit by lazy {
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
    }
}

object MealApiClient {
    val mealApiService: MealApiService by lazy {
        RetrofitClient.retrofit.create(MealApiService::class.java)
    }
}