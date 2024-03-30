package com.example.easyfood.repository

import androidx.lifecycle.LiveData
import com.example.easyfood.database.FavoriteMealDatabase
import com.example.easyfood.pojo.Meal

class FavoriteMealRepository(private val db: FavoriteMealDatabase) {
    suspend fun addMealToFavorites(meal: Meal) = db.getFavoriteMealDAO().addMealToFavorites(meal)
    suspend fun removeMealFromFavorites(meal: Meal) = db.getFavoriteMealDAO().removeMealFromFavorites(meal)
    fun getAllFavoriteMeals(): LiveData<List<Meal>> = db.getFavoriteMealDAO().getAllFavoriteMeals()
}