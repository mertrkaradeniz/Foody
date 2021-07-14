package com.example.foody.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foody.util.Constants.FAVORITE_RECIPES_TABLE

@Entity(tableName = FAVORITE_RECIPES_TABLE)
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var result: Result
)