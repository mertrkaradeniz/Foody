package com.example.foody.ui.ingredient

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foody.adapters.IngredientsAdapter
import com.example.foody.data.model.Result
import com.example.foody.databinding.FragmentIngredientsBinding
import com.example.foody.util.Constants.RECIPE_RESULT_KEY


class IngredientsFragment : Fragment() {

    private var _binding: FragmentIngredientsBinding? = null
    private val binding get() = _binding!!
    private val mAdapter by lazy { IngredientsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIngredientsBinding.inflate(inflater, container, false)
        val bundle: Result? = arguments?.getParcelable(RECIPE_RESULT_KEY)
        setupRecyclerView()
        bundle?.extendedIngredients?.let { mAdapter.setData(it) }
        return binding.root
    }

    private fun setupRecyclerView() {
        binding.rvIngredients.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}