package com.example.foody.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foody.data.model.FoodRecipe
import com.example.foody.util.Constants.RECIPES_TABLE

@Entity(tableName = RECIPES_TABLE)
class RecipesEntity(
    var foodRecipe: FoodRecipe
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}