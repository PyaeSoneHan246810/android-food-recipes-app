package com.example.easyfood.fragments.bottomSheet

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.easyfood.R
import com.example.easyfood.activities.MainActivity
import com.example.easyfood.activities.MealActivity
import com.example.easyfood.databinding.FragmentMealInfoBottomSheetBinding
import com.example.easyfood.fragments.HomeFragment
import com.example.easyfood.viewModel.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

private const val MEAL_ID = "MEAL_ID"
private const val MEAL_NAME = "MEAL_NAME"
private const val MEAL_AREA = "MEAL_AREA"
private const val MEAL_CATEGORY = "MEAL_CATEGORY"
private const val MEAL_THUMB = "MEAL_THUMB"

class MealInfoBottomSheetFragment : BottomSheetDialogFragment() {
    private var mealId: String? = null
    private var mealName: String? = null
    private var mealArea: String? = null
    private var mealCategory: String? = null
    private var mealThumb: String? = null
    private var _mealBottomSheetBinding: FragmentMealInfoBottomSheetBinding? = null
    private val mealBottomSheetBinding get() = _mealBottomSheetBinding!!

    companion object {
        @JvmStatic
        fun newInstance(mealId: String, mealName: String?, mealArea: String?, mealCategory: String?, mealThumb: String?) =
            MealInfoBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(MEAL_ID, mealId)
                    putString(MEAL_NAME, mealName)
                    putString(MEAL_AREA, mealArea)
                    putString(MEAL_CATEGORY, mealCategory)
                    putString(MEAL_THUMB, mealThumb)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mealId = it.getString(MEAL_ID)
            mealName = it.getString(MEAL_NAME)
            mealArea = it.getString(MEAL_AREA)
            mealCategory = it.getString(MEAL_CATEGORY)
            mealThumb = it.getString(MEAL_THUMB)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _mealBottomSheetBinding = FragmentMealInfoBottomSheetBinding.inflate(inflater, container, false)
        return mealBottomSheetBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMealInfoToViews()
        onReadMoreClicked()
    }

    private fun setupMealInfoToViews(){
        mealBottomSheetBinding.tvBottomSheetMealName.text = mealName
        mealBottomSheetBinding.tvBottomSheetMealArea.text = mealArea
        mealBottomSheetBinding.tvBottomSheetMealCategory.text = mealCategory
        Glide
            .with(this@MealInfoBottomSheetFragment)
            .load(mealThumb)
            .centerCrop()
            .placeholder(R.drawable.img_meal_placeholder)
            .error(R.drawable.img_error)
            .into(mealBottomSheetBinding.ivBottomSheetMeal)
    }

    private fun onReadMoreClicked(){
        mealBottomSheetBinding.tvBottomSheetReadMore.setOnClickListener {
            val intent = Intent(activity, MealActivity::class.java)
            intent.apply {
                putExtra(HomeFragment.MEAL_ID, mealId)
                putExtra(HomeFragment.MEAL_NAME, mealName)
                putExtra(HomeFragment.MEAL_THUMB, mealThumb)
            }
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _mealBottomSheetBinding = null
    }
}