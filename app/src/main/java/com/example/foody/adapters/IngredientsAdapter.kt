package com.example.foody.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.foody.R
import com.example.foody.data.model.ExtendedIngredient
import com.example.foody.databinding.IngredientsRowLayoutBinding
import com.example.foody.util.Constants.BASE_IMAGE_URL
import com.example.foody.util.RecipesDiffUtil
import java.util.*

class IngredientsAdapter : RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder>() {

    private var ingredientsList = emptyList<ExtendedIngredient>()

    inner class IngredientsViewHolder(val binding: IngredientsRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder {
        return IngredientsViewHolder(
            IngredientsRowLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {
        holder.binding.apply {
            imgIngredient.load(BASE_IMAGE_URL + ingredientsList[position].image) {
                crossfade(600)
                error(R.drawable.ic_error_placeholder)
            }
            tvIngredientName.text = ingredientsList[position].name.replaceFirstChar {
                if (it.isLowerCase())
                    it.titlecase(Locale.getDefault())
                else it.toString()
            }
            tvIngredientAmount.text = ingredientsList[position].amount.toString()
            tvIngredientUnit.text = ingredientsList[position].unit
            tvIngredientConsistency.text = ingredientsList[position].consistency
            tvIngredientOriginal.text = ingredientsList[position].original
        }
    }


    override fun getItemCount() = ingredientsList.size

    fun setData(newIngredients: List<ExtendedIngredient>) {
        val ingredientsDiffUtil = RecipesDiffUtil(ingredientsList, newIngredients)
        val diffUtilResult = DiffUtil.calculateDiff(ingredientsDiffUtil)
        ingredientsList = newIngredients
        diffUtilResult.dispatchUpdatesTo(this)
    }
}