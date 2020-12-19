package com.example.itunesearch.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.itunesearch.App
import com.example.itunesearch.R
import com.example.itunesearch.databinding.ActivityMainBinding
import com.example.itunesearch.di.component.DaggerMainActivityComponent
import com.example.itunesearch.di.component.MainActivityComponent
import com.example.itunesearch.di.module.MainActivityModule
import com.example.itunesearch.ui.home.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    // To prevent duplicate click
    private var currentFragmentId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        getDependency()

        binding.viewPager.isUserInputEnabled = false
        binding.viewPager.adapter = ScreenSlidePagerAdapter(this)
        binding.navigation.setOnNavigationItemSelectedListener(navigationListener)

    }

    private fun getDependency() {
        val component: MainActivityComponent = DaggerMainActivityComponent
            .builder().applicationComponent(App.get(this)?.getComponent())
            .mainActivityModule(MainActivityModule(this))
            .build()
        component.injectMainActivity(this)
    }


    private val navigationListener =
        BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            if (currentFragmentId == menuItem.itemId) {
                return@OnNavigationItemSelectedListener false
            }
            currentFragmentId = menuItem.itemId
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    binding.viewPager.setCurrentItem(0, true)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_saved -> {
                    binding.viewPager.setCurrentItem(1, true)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    private inner class ScreenSlidePagerAdapter(m: MainActivity) : FragmentStateAdapter(m) {
        private val pages = 2

        override fun getItemCount(): Int = pages

        override fun createFragment(position: Int): Fragment = getFragment(position)

        private fun getFragment(position: Int): Fragment {
            return when (position) {
                0 -> {
                    HomeFragment()
                }
                1 -> {
                    Fragment()
                }
                else -> {
                    Fragment()
                }
            }
        }
    }

    companion object {
        const val TAG = "MainActivity"
    }
}
