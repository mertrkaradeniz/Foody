package com.example.foody.ui.overview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import coil.load
import com.example.foody.R
import com.example.foody.data.model.Result
import com.example.foody.databinding.FragmentOverviewBinding
import com.example.foody.util.Constants.RECIPE_RESULT_KEY
import org.jsoup.Jsoup

class OverviewFragment : Fragment() {

    private var _binding: FragmentOverviewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOverviewBinding.inflate(inflater, container, false)
        bindData()
        return binding.root
    }

    private fun bindData() {
        val bundle: Result? = arguments?.getParcelable(RECIPE_RESULT_KEY)
        binding.apply {
            imgMain.load(bundle?.image)
            tvTitle.text = bundle?.title
            tvLikes.text = bundle?.aggregateLikes.toString()
            tvTime.text = bundle?.readyInMinutes.toString()
            bundle?.summary?.let {
                tvSummary.text = Jsoup.parse(it).text()
            }
            setColorDietType(imgVegetarian, tvVegetarian, bundle?.vegetarian)
            setColorDietType(imgVegan, tvVegan, bundle?.vegan)
            setColorDietType(imgGlutenFree, tvGlutenFree, bundle?.glutenFree)
            setColorDietType(imgDairyFree, tvDairyFree, bundle?.dairyFree)
            setColorDietType(imgHealthy, tvHealthy, bundle?.veryHealthy)
            setColorDietType(imgCheap, tvCheap, bundle?.cheap)
        }
    }

    private fun setColorDietType(imageView: ImageView, textView: TextView, value: Boolean?) {
        value?.let {
            if (value) {
                imageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
                textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}