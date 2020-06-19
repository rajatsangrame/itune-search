package com.example.itunesearch.di.component

import com.example.itunesearch.data.Repository
import com.example.itunesearch.di.ApplicationScope
import com.example.itunesearch.di.module.ApplicationModule
import dagger.Component


/**
 * Created by Rajat Sangrame on 19/6/20.
 * http://github.com/rajatsangrame
 */
@ApplicationScope
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun getRepository(): Repository?

}