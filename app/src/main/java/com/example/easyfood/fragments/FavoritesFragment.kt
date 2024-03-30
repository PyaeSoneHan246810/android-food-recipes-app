package com.example.easyfood.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.easyfood.R
import com.example.easyfood.activities.MainActivity
import com.example.easyfood.activities.MealActivity
import com.example.easyfood.adapter.RecyclerViewAdapter
import com.example.easyfood.databinding.FragmentFavoritesBinding
import com.example.easyfood.databinding.LayoutMealItemBinding
import com.example.easyfood.pojo.Meal
import com.example.easyfood.viewModel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class FavoritesFragment : Fragment(R.layout.fragment_favorites) {
    private var _favoritesBinding: FragmentFavoritesBinding? = null
    private val favoritesBinding get() = _favoritesBinding!!
    private lateinit var mainViewModel: MainViewModel
    private lateinit var favoriteMealRecyclerViewAdapter: RecyclerViewAdapter<Meal>
    private lateinit var layoutMealItemBinding: LayoutMealItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = (activity as MainActivity).mainViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _favoritesBinding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return favoritesBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeFavoriteMeals()
    }

    private fun observeFavoriteMeals() {
        mainViewModel.observeFavoriteMealsLiveData().observe(viewLifecycleOwner) {favoriteMeals ->
            setupFavoriteMealsRecyclerView(favoriteMeals)
            updateUI(favoriteMeals)
        }
    }

    private fun setupFavoriteMealsRecyclerView(favoriteMeals: List<Meal>) {
        val verticalGridLayoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        favoriteMealRecyclerViewAdapter = RecyclerViewAdapter(R.layout.layout_meal_item, favoriteMeals, true) { view, favoriteMeal, _ ->
            layoutMealItemBinding = LayoutMealItemBinding.bind(view)
            layoutMealItemBinding.tvMealName.text = favoriteMeal.strMeal
            Glide
                .with(this@FavoritesFragment)
                .load(favoriteMeal.strMealThumb)
                .centerCrop()
                .placeholder(R.drawable.img_meal_placeholder)
                .error(R.drawable.img_error)
                .into(layoutMealItemBinding.ivMealImage)
            onFavoriteMealClicked(favoriteMeal)
        }
        favoritesBinding.rvFavoriteMeals.apply {
            adapter = favoriteMealRecyclerViewAdapter
            setHasFixedSize(true)
            layoutManager = verticalGridLayoutManager
            post {
                onFavoriteMealSwiped()
            }
        }
    }

    private fun updateUI(favoriteMeals: List<Meal>) {
        if (favoriteMeals.isNotEmpty()) {
            favoritesBinding.llEmptyFavoriteMeals.visibility = View.GONE
            favoritesBinding.rvFavoriteMeals.visibility = View.VISIBLE
        } else {
            favoritesBinding.llEmptyFavoriteMeals.visibility = View.VISIBLE
            favoritesBinding.rvFavoriteMeals.visibility = View.GONE
        }
    }

    private fun onFavoriteMealClicked(favoriteMeal: Meal){
        layoutMealItemBinding.clMealItem.setOnClickListener {
            val intent = Intent(requireContext(), MealActivity::class.java)
            intent.apply {
                putExtra(HomeFragment.MEAL_ID, favoriteMeal.idMeal)
                putExtra(HomeFragment.MEAL_NAME, favoriteMeal.strMeal)
                putExtra(HomeFragment.MEAL_THUMB, favoriteMeal.strMealThumb)
            }
            startActivity(intent)
        }
    }

    private fun onFavoriteMealSwiped(){
        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val mealToBeRemoved = favoriteMealRecyclerViewAdapter.items[position]
                mainViewModel.removeMealFromFavorites(mealToBeRemoved)
                Snackbar
                    .make(requireView(), "Successfully removed from Favorites", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(requireContext().getColor(R.color.off_black))
                    .setTextColor(requireContext().getColor(R.color.off_white))
                    .setAction("Undo"){
                        mainViewModel.undoRemovingFromFavorites(mealToBeRemoved)
                    }
                    .setActionTextColor(requireContext().getColor(R.color.accent))
                    .show()
            }
        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(favoritesBinding.rvFavoriteMeals)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _favoritesBinding = null
    }
}