package com.example.foody.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.example.foody.R
import com.example.foody.adapters.PagerAdapter
import com.example.foody.databinding.ActivityDetailsBinding
import com.example.foody.ui.ingredient.IngredientsFragment
import com.example.foody.ui.instruction.InstructionsFragment
import com.example.foody.ui.main.MainViewModel
import com.example.foody.ui.overview.OverviewFragment
import com.example.foody.util.Constants.RECIPE_RESULT_KEY
import com.google.android.material.tabs.TabLayoutMediator

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private val args by navArgs<DetailsActivityArgs>()
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolbar()
        setupViewPager()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupViewPager() {
        val fragments = ArrayList<Fragment>()
        fragments.add(OverviewFragment())
        fragments.add(IngredientsFragment())
        fragments.add(InstructionsFragment())

        val titles = ArrayList<String>()
        titles.add("Overview")
        titles.add("Ingredients")
        titles.add("Instructions")

        val resultBundle = Bundle()
        resultBundle.putParcelable(RECIPE_RESULT_KEY, args.result)

        val pagerAdapter = PagerAdapter(
            resultBundle,
            fragments,
            this
        )
        binding.viewPager2.apply {
            isUserInputEnabled = false
            adapter = pagerAdapter
        }

        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == item.itemId) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}