package com.example.itunesearch

import android.app.Application
import android.content.Context
import com.example.itunesearch.di.component.ApplicationComponent
import com.example.itunesearch.di.component.DaggerApplicationComponent
import com.example.itunesearch.di.module.ContextModule


/**
 * Created by Rajat Sangrame on 19/6/20.
 * http://github.com/rajatsangrame
 */
class App : Application() {

    private var component: ApplicationComponent? = null

    operator fun get(context: Context): App? {
        return context.applicationContext as App
    }

    override fun onCreate() {
        super.onCreate()
        component = DaggerApplicationComponent
            .builder()
            .contextModule(ContextModule(this))
            .build()
    }

    fun getComponent(): ApplicationComponent? {
        return component
    }
}