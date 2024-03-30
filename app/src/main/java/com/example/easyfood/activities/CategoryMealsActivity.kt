package com.example.easyfood.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.easyfood.R
import com.example.easyfood.adapter.RecyclerViewAdapter
import com.example.easyfood.databinding.ActivityCategoryMealsBinding
import com.example.easyfood.databinding.LayoutMealItemBinding
import com.example.easyfood.fragments.HomeFragment
import com.example.easyfood.pojo.MealByCategory
import com.example.easyfood.viewModel.CategoryMealsViewModel

class CategoryMealsActivity : AppCompatActivity() {
    private lateinit var activityCategoryMealsBinding: ActivityCategoryMealsBinding
    private lateinit var categoryMealsViewModel: CategoryMealsViewModel
    private lateinit var categoryMealsRecyclerViewAdapter: RecyclerViewAdapter<MealByCategory>
    private lateinit var layoutMealItemBinding: LayoutMealItemBinding
    private var categoryName: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityCategoryMealsBinding = ActivityCategoryMealsBinding.inflate(layoutInflater)
        val contentView = activityCategoryMealsBinding.root
        setContentView(contentView)

        categoryMealsViewModel = ViewModelProviders.of(this)[CategoryMealsViewModel::class.java]

        getCategoryInfoFromIntent()

        categoryMealsViewModel.getMealsByCategory(categoryName)

        observeCategoryMeals()
    }

    private fun getCategoryInfoFromIntent() {
        val intent = intent
        categoryName = intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!
    }

    private fun observeCategoryMeals() {
        categoryMealsViewModel.observeCategoryMealsLiveData().observe(this){mealsByCategory ->
            val categoryMealsCountDisplayText = "$categoryName: ${mealsByCategory.size}"
            activityCategoryMealsBinding.tvCategoryMealsCount.text = categoryMealsCountDisplayText
            setUpCategoryMealsRecyclerView(mealsByCategory)
        }
    }

    private fun setUpCategoryMealsRecyclerView(mealsByCategory: List<MealByCategory>) {
        val verticalGridLayoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        categoryMealsRecyclerViewAdapter = RecyclerViewAdapter(R.layout.layout_meal_item, mealsByCategory, true){ view, mealByCategory, _ ->
            layoutMealItemBinding = LayoutMealItemBinding.bind(view)
            layoutMealItemBinding.tvMealName.text = mealByCategory.strMeal
            Glide
                .with(this)
                .load(mealByCategory.strMealThumb)
                .centerCrop()
                .placeholder(R.drawable.img_meal_placeholder)
                .error(R.drawable.img_error)
                .into(layoutMealItemBinding.ivMealImage)
            onCategoryMealClicked(mealByCategory)
        }
        activityCategoryMealsBinding.rvCategoryMeals.apply {
            adapter = categoryMealsRecyclerViewAdapter
            setHasFixedSize(true)
            layoutManager = verticalGridLayoutManager
        }
    }

    private fun onCategoryMealClicked(mealByCategory: MealByCategory){
        layoutMealItemBinding.clMealItem.setOnClickListener {
            val intent = Intent(this, MealActivity::class.java)
            intent.apply {
                putExtra(HomeFragment.MEAL_ID, mealByCategory.idMeal)
                putExtra(HomeFragment.MEAL_NAME, mealByCategory.strMeal)
                putExtra(HomeFragment.MEAL_THUMB, mealByCategory.strMealThumb)
            }
            startActivity(intent)
        }
    }
}