package com.example.foody.util

import androidx.recyclerview.widget.DiffUtil
import com.example.foody.data.model.Result

class RecipesDiffUtil(
    private val oldList: List<Result>,
    private val newList: List<Result>,
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}