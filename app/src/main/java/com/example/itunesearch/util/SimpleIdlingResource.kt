package com.example.itunesearch.util

import androidx.test.espresso.idling.CountingIdlingResource
import com.example.itunesearch.BuildConfig

/**
 * Created by Rajat Sangrame on 20/6/20.
 * http://github.com/rajatsangrame
 */
object SimpleIdlingResource {

    private const val RESOURCE = "GLOBAL"

    @JvmField
    val countingIdlingResource = CountingIdlingResource(RESOURCE)

    fun increment() {
        if (BuildConfig.DEBUG) {
            countingIdlingResource.increment()
        }
    }

    fun decrement() {
        if (!countingIdlingResource.isIdleNow && BuildConfig.DEBUG) {
            countingIdlingResource.decrement()
        }
    }
}