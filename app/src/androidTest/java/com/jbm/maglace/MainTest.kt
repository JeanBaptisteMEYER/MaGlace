package com.jbm.maglace

import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.junit.Rule


@RunWith(AndroidJUnit4::class)
class MainTest {

    @get:Rule
    val activityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun showList() {
        onView(withId(R.id.navigation_list)).perform(click())

        //TODO change to an automated checking process
        Thread.sleep(5000)
    }
}