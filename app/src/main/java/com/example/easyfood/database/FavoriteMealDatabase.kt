package com.example.easyfood.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.easyfood.pojo.Meal

@Database(entities = [Meal::class], version = 1)
@TypeConverters(FavoriteMealTypeConverter::class)
abstract class FavoriteMealDatabase: RoomDatabase(){
    abstract fun getFavoriteMealDAO(): FavoriteMealDAO
    companion object {
        @Volatile
        private var INSTANCE: FavoriteMealDatabase? = null

        @Synchronized
        fun getInstance(context: Context): FavoriteMealDatabase {
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(context, FavoriteMealDatabase::class.java, "favorite_meal_db").fallbackToDestructiveMigration().build()
            }
            return INSTANCE as FavoriteMealDatabase
        }
    }
}