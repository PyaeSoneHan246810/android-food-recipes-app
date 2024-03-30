package com.example.easyfood.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.easyfood.R
import com.example.easyfood.database.FavoriteMealDatabase
import com.example.easyfood.databinding.ActivityMealBinding
import com.example.easyfood.fragments.HomeFragment
import com.example.easyfood.pojo.Meal
import com.example.easyfood.repository.FavoriteMealRepository
import com.example.easyfood.viewModel.MealViewModel
import com.example.easyfood.viewModel.MealViewModelFactory
import com.google.android.material.snackbar.Snackbar

class MealActivity : AppCompatActivity() {
    private lateinit var activityMealBinding: ActivityMealBinding
    private lateinit var mealViewModel: MealViewModel
    private var mealId: String = ""
    private var mealName: String = ""
    private var mealThumb: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMealBinding = ActivityMealBinding.inflate(layoutInflater)
        val contentView = activityMealBinding.root
        setContentView(contentView)

        val favoriteMealDatabase = FavoriteMealDatabase.getInstance(this)
        val favoriteMealRepository = FavoriteMealRepository(favoriteMealDatabase)
        val mealViewModelFactory = MealViewModelFactory(favoriteMealRepository)
        mealViewModel = ViewModelProvider(this, mealViewModelFactory)[MealViewModel::class.java]

        getMealInfoFromIntent()

        setUpMealInfoToViews()

        onLoadingState()

        mealViewModel.getMealDetailsById(mealId)

        observeMealDetails()
    }

    private fun getMealInfoFromIntent(){
        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }

    private fun setUpMealInfoToViews(){
        Glide
            .with(this)
            .load(mealThumb)
            .centerCrop()
            .placeholder(R.drawable.img_meal_placeholder)
            .error(R.drawable.img_error)
            .into(activityMealBinding.ivMeal)

        activityMealBinding.ctlMeal.title = mealName
        activityMealBinding.ctlMeal.setExpandedTitleColor(getColor(R.color.white))
        activityMealBinding.ctlMeal.setCollapsedTitleTextColor(getColor(R.color.white))
    }

    private fun observeMealDetails() {
        mealViewModel.observeMealDetailsLiveData().observe(this){meal ->
            onResponseState()
            setupMealDetailsToViews(meal)
            onYouTubeImageClicked(meal)
            onAddToFavoriteButtonClicked(meal)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupMealDetailsToViews(meal: Meal) {
        activityMealBinding.tvMealCategory.text = "Category: ${meal.strCategory}"
        activityMealBinding.tvMealArea.text = "Area: ${meal.strArea}"
        activityMealBinding.tvMealInstructions.text = meal.strInstructions
    }

    private fun onLoadingState() {
        activityMealBinding.progressBar.visibility = View.VISIBLE
        activityMealBinding.fabAddToFavorites.visibility = View.INVISIBLE
        activityMealBinding.tvMealInstructionsTitle.visibility = View.INVISIBLE
        activityMealBinding.tvMealInstructions.visibility = View.INVISIBLE
        activityMealBinding.ivYoutubeIcon.visibility = View.INVISIBLE
    }

    private fun onResponseState() {
        activityMealBinding.progressBar.visibility = View.INVISIBLE
        activityMealBinding.fabAddToFavorites.visibility = View.VISIBLE
        activityMealBinding.tvMealInstructionsTitle.visibility = View.VISIBLE
        activityMealBinding.tvMealInstructions.visibility = View.VISIBLE
        activityMealBinding.ivYoutubeIcon.visibility = View.VISIBLE
    }

    private fun onYouTubeImageClicked(meal: Meal){
        activityMealBinding.ivYoutubeIcon.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(meal.strYoutube))
            startActivity(intent)
        }
    }

    private fun onAddToFavoriteButtonClicked(meal: Meal) {
        activityMealBinding.fabAddToFavorites.setOnClickListener {
            mealViewModel.addMealToFavorites(meal)
            Snackbar
                .make(activityMealBinding.root, "Successfully saved to Favorites", Snackbar.LENGTH_SHORT)
                .setBackgroundTint(getColor(R.color.off_black))
                .setTextColor(getColor(R.color.off_white))
                .show()
        }
    }
}