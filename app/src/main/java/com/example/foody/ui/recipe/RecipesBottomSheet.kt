package com.example.foody.ui.recipe

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.foody.R
import com.example.foody.databinding.RecipesBottomSheetBinding
import com.example.foody.util.Constants.DEFAULT_DIET_TYPE
import com.example.foody.util.Constants.DEFAULT_MEAL_TYPE
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception
import java.util.*

@AndroidEntryPoint
class RecipesBottomSheet : BottomSheetDialogFragment() {

    private var _binding: RecipesBottomSheetBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RecipesViewModel by viewModels()
    private var mealTypeChip = DEFAULT_MEAL_TYPE
    private var mealTypeChipId = 0
    private var dietTypeChip = DEFAULT_DIET_TYPE
    private var dietTypeChipId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = RecipesBottomSheetBinding.inflate(inflater, container, false)
        setupObserver()
        setupListeners()
        return binding.root
    }

    private fun setupObserver() {
        viewModel.readMealAndDietType.asLiveData().observe(viewLifecycleOwner, { value ->
            mealTypeChip = value.selectedMealType
            dietTypeChip = value.selectedDietType
            updateChip(value.selectedMealTypeId, binding.chipGroupMealType)
            updateChip(value.selectedDietTypeId, binding.chipGroupDietType)
        })
    }

    private fun updateChip(chipId: Int, chipGroup: ChipGroup) {
        if (chipId != 0) {
            try {
                chipGroup.findViewById<Chip>(chipId).isChecked = true
            } catch (e: Exception) {
                Log.d("RecipesBottomSheet", e.message.toString())
            }
        }
    }

    private fun setupListeners() {
        binding.chipGroupMealType.setOnCheckedChangeListener { group, selectedChipId ->
            val chip = group.findViewById<Chip>(selectedChipId)
            val selectedMealType = chip.text.toString().lowercase()
            mealTypeChip = selectedMealType
            mealTypeChipId = selectedChipId
        }
        binding.chipGroupDietType.setOnCheckedChangeListener { group, selectedChipId ->
            val chip = group.findViewById<Chip>(selectedChipId)
            val selectedDietType = chip.text.toString().lowercase()
            dietTypeChip = selectedDietType
            dietTypeChipId = selectedChipId
        }
        binding.btnApply.setOnClickListener {
            viewModel.saveMealAndDietType(
                mealTypeChip,
                mealTypeChipId,
                dietTypeChip,
                dietTypeChipId
            )
            val action =
                RecipesBottomSheetDirections.actionRecipesBottomSheetToRecipesFragment(true)
            findNavController().navigate(action)
        }
    }

}