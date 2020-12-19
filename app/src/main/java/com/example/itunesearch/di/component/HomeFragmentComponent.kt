package com.example.itunesearch.di.component

import com.example.itunesearch.di.MainActivityScope
import com.example.itunesearch.di.module.HomeFragmentModule
import com.example.itunesearch.di.module.MainActivityModule
import com.example.itunesearch.ui.home.HomeFragment
import com.example.itunesearch.ui.main.MainActivity
import dagger.Component


/**
 * Created by Rajat Sangrame on 19/6/20.
 * http://github.com/rajatsangrame
 */
@Component(modules = [HomeFragmentModule::class], dependencies = [ApplicationComponent::class])
@MainActivityScope
interface HomeFragmentComponent {
    fun injectFragment(fragment: HomeFragment?)
}