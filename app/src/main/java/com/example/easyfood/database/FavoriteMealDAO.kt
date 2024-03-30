package com.example.easyfood.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.easyfood.pojo.Meal

@Dao
interface FavoriteMealDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMealToFavorites(meal: Meal)

    @Delete
    suspend fun removeMealFromFavorites(meal: Meal)

    @Query("SELECT * FROM favoriteMeals")
    fun getAllFavoriteMeals(): LiveData<List<Meal>>
}