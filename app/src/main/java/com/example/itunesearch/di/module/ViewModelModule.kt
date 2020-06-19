package com.example.itunesearch.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.itunesearch.di.ViewModelKey
import com.example.itunesearch.ui.MainViewModel
import com.example.itunesearch.util.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


/**
 * Created by Rajat Sangrame on 19/6/20.
 * http://github.com/rajatsangrame
 */
@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory?): ViewModelProvider.Factory?

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun provideHomeViewModel(homeViewModel: MainViewModel?): ViewModel?

}