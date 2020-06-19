package com.example.itunesearch.di.component

import com.example.itunesearch.di.MainActivityScope
import com.example.itunesearch.di.module.MainActivityModule
import com.example.itunesearch.ui.MainActivity
import dagger.Component


/**
 * Created by Rajat Sangrame on 19/6/20.
 * http://github.com/rajatsangrame
 */
@Component(modules = [MainActivityModule::class], dependencies = [ApplicationComponent::class])
@MainActivityScope
interface MainActivityComponent {
    fun injectMainActivity(mainActivity: MainActivity?)
}