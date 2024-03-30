package com.example.easyfood.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.easyfood.repository.FavoriteMealRepository

class MainViewModelFactory(
    private val favoriteMealRepository: FavoriteMealRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(favoriteMealRepository) as T
    }
}