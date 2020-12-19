package com.example.itunesearch.di.module

import com.example.itunesearch.BuildConfig
import com.example.itunesearch.adapter.TrackAdapter
import com.example.itunesearch.di.MainActivityScope
import com.example.itunesearch.ui.home.HomeFragment
import com.example.itunesearch.ui.main.MainActivity
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import dagger.Module
import dagger.Provides


/**
 * Created by Rajat Sangrame on 19/6/20.
 * http://github.com/rajatsangrame
 */
@Module
class HomeFragmentModule(var fragment: HomeFragment) {

    @Provides
    @MainActivityScope
    fun getHttpDataSourceFactory(): DefaultHttpDataSourceFactory {
        return DefaultHttpDataSourceFactory("exoplayer-" + BuildConfig.APPLICATION_ID)
    }

    @Provides
    @MainActivityScope
    fun getAdapter(): TrackAdapter {
        val trackAdapter = TrackAdapter()
        trackAdapter.setHasStableIds(true)
        return trackAdapter
    }

}