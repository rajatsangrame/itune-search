package com.example.itunesearch

import android.content.Intent
import android.util.Log
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.pressImeActionButton
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.itunesearch.ui.main.MainActivity
import com.example.itunesearch.util.SimpleIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Rajat Sangrame on 20/6/20.
 * http://github.com/rajatsangrame
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private var mainActivity: MainActivity? = null

    @JvmField
    val idlingResource = SimpleIdlingResource.countingIdlingResource
    val artists = arrayOf(
        "Taylor Swift",
        "Ed Sheeran",
        "Rajat Sangrame",
        "Enrique Iglesias",
        "Coldplay"
    )

    @JvmField
    @Rule
    var testRule = ActivityTestRule(MainActivity::class.java, false, true)

    @Before
    fun init() {
        Log.i(TAG, "@before init: ")
        val intent = Intent(Intent.ACTION_PICK)
        intent.putExtra("parameter", "Value")
        mainActivity = testRule.launchActivity(intent)
        IdlingRegistry.getInstance().register(idlingResource)
    }

    @After
    fun reset() {
        Log.i(TAG, "@after reset: ")
        mainActivity = null
        IdlingRegistry.getInstance().unregister(idlingResource)
    }

    /**
     * 5 Test cases in sequence asked in Task
     */
    @Test
    fun performTest() {

        artists.forEach {

            Log.d(TAG, "performTest: ")
            onView(withId(R.id.et_search)).perform(ViewActions.typeText(it))
            onView(withId(R.id.et_search)).perform(pressImeActionButton())
            onView(withId(R.id.btn_clear)).perform(click())

        }
    }

    companion object {
        private const val TAG = "MainActivityTest"
    }
}