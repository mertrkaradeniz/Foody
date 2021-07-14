package com.example.foody.ui.instruction

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.example.foody.data.model.Result
import com.example.foody.databinding.FragmentInstructionsBinding
import com.example.foody.util.Constants.RECIPE_RESULT_KEY


class InstructionsFragment : Fragment() {

    private var _binding: FragmentInstructionsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInstructionsBinding.inflate(inflater, container, false)
        setupWebView()
        return binding.root
    }

    private fun setupWebView() {
        val bundle: Result? = arguments?.getParcelable(RECIPE_RESULT_KEY)
        binding.webViewInstruction.webViewClient = object : WebViewClient() {}
        bundle?.sourceUrl?.let { binding.webViewInstruction.loadUrl(it) }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}