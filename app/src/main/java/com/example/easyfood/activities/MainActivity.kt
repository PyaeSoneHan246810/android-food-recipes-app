package com.example.easyfood.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.easyfood.R
import com.example.easyfood.database.FavoriteMealDatabase
import com.example.easyfood.databinding.ActivityMainBinding
import com.example.easyfood.repository.FavoriteMealRepository
import com.example.easyfood.viewModel.MainViewModel
import com.example.easyfood.viewModel.MainViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding
    val mainViewModel: MainViewModel by lazy {
        val favoriteMealDatabase = FavoriteMealDatabase.getInstance(this)
        val favoriteMealRepository = FavoriteMealRepository(favoriteMealDatabase)
        val mainViewModelFactory = MainViewModelFactory(favoriteMealRepository)
        ViewModelProvider(this, mainViewModelFactory)[MainViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        val contentView = activityMainBinding.root
        setContentView(contentView)

        val bnvMain = activityMainBinding.bnvMain
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fcvMain) as NavHostFragment
        val navController = navHostFragment.navController

        NavigationUI.setupWithNavController(bnvMain, navController)
    }
}