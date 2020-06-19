package com.example.itunesearch.di.module

import android.content.Context
import com.example.itunesearch.di.ApplicationScope
import com.example.itunesearch.di.ApplicationContext
import dagger.Module
import dagger.Provides


/**
 * Created by Rajat Sangrame on 19/6/20.
 * http://github.com/rajatsangrame
 */
@Module
class ContextModule(var context: Context) {

    @ApplicationContext
    @ApplicationScope
    @Provides
    fun context(): Context {
        return context.applicationContext
    }
}