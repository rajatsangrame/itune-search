package com.example.itunesearch

import androidx.test.espresso.IdlingResource
import java.util.concurrent.atomic.AtomicBoolean


/**
 * Created by Rajat Sangrame on 20/6/20.
 * http://github.com/rajatsangrame
 */
class SimpleIdlingResource : IdlingResource {

    @Volatile
    private var callback: IdlingResource.ResourceCallback? = null

    // Idleness is controlled with this boolean.
    private val isIdleNow = AtomicBoolean(true)

    override fun getName(): String {
        return TAG
    }

    override fun isIdleNow(): Boolean {
        return isIdleNow.get()
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        this.callback = callback
    }

    companion object {
        private const val TAG = "SimpleTimeIdlingResource"
    }
}