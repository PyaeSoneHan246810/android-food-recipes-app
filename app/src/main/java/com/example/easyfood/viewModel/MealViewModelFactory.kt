package com.example.easyfood.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.easyfood.repository.FavoriteMealRepository

class MealViewModelFactory(
    private val favoriteMealRepository: FavoriteMealRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MealViewModel(favoriteMealRepository) as T
    }
}