package com.example.foody.bindingadapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.foody.data.local.RecipesEntity
import com.example.foody.data.model.FoodRecipe
import com.example.foody.util.Resource

class RecipesBinding {

    companion object {

        @BindingAdapter("android:readApiResponseImage", "android:readDatabaseImage", requireAll = true)
        @JvmStatic
        fun errorImageViewVisibility(
            imageView: ImageView,
            apiResponse: Resource<FoodRecipe>?,
            database: List<RecipesEntity>?
        ) {
            if (apiResponse is Resource.Error && database.isNullOrEmpty()) {
                imageView.visibility = View.VISIBLE
            } else if (apiResponse is Resource.Loading) {
                imageView.visibility = View.GONE
            } else if (apiResponse is Resource.Success) {
                imageView.visibility = View.GONE
            }
        }

        @BindingAdapter("android:readApiResponseText", "android:readDatabaseText", requireAll = true)
        @JvmStatic
        fun errorTextViewVisibility(
            textView: TextView,
            apiResponse: Resource<FoodRecipe>?,
            database: List<RecipesEntity>?
        ) {
            if (apiResponse is Resource.Error && database.isNullOrEmpty()) {
                textView.visibility = View.VISIBLE
                textView.text = apiResponse.message.toString()
            } else if (apiResponse is Resource.Loading) {
                textView.visibility = View.GONE
            } else if (apiResponse is Resource.Success) {
                textView.visibility = View.GONE
            }
        }
    }
}