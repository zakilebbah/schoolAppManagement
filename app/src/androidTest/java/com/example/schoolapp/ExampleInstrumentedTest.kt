package com.example.schoolapp

import android.view.View
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule
import org.hamcrest.BaseMatcher
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    private fun <T> first(matcher: Matcher<T>): Matcher<T>? {
        return object : BaseMatcher<T>() {
            var isFirst = true
            override fun matches(item: Any): Boolean {
                if (isFirst && matcher.matches(item)) {
                    isFirst = false
                    return true
                }
                return false
            }

            override fun describeTo(description: Description) {
                description.appendText("should return first matching item")
            }
        }
    }
    @get:Rule
    public val mActivityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)
    @Test
    fun useAppContext() {
        onView(withId(R.id.fab)).perform(click())
        onView(withId(R.id.name)).perform(typeText("classe TEST"))
        onView(withId(R.id.grade)).perform(typeText("classe GRADE"))
        onView(withId(R.id.button_save)).perform(click())
        onView(first(withText("classe TEST"))).check(matches(isDisplayed()))
        onView(first(withText("classe TEST"))).perform(click())
        for (i in 1..3) {
            onView(withId(R.id.fab)).perform(click())
            onView(withId(R.id.nom)).perform(typeText("Nom" + i.toString()))
            onView(withId(R.id.prenom)).perform(typeText("Prenom"+ i.toString()))
            onView(isRoot()).perform(ViewActions.closeSoftKeyboard())
            onView(withId(R.id.button_save)).perform(click())
        }
        onView(isRoot()).perform(ViewActions.pressBack())

        onView(withId(R.id.navigation_notifications)).perform(click())
        onView(withId(R.id.fab)).perform(click())
        onView(withId(R.id.nom)).perform(typeText("Matiere 1"))
        onView(withId(R.id.coef)).perform(typeText("2"))
        onView(isRoot()).perform(ViewActions.closeSoftKeyboard())
        onView(withId(R.id.button_save)).perform(click())
        onView(first(withText("Matiere 1"))).check(matches(isDisplayed()))
        onView(first(withText("Matiere 1"))).perform(click())
        onView(withId(R.id.button)).perform(click())
        onView(withId(R.id.nom)).perform(typeText("Exam 1"))
        onView(withId(R.id.button_save)).perform(click())
        onView(first(withText("Exam 1"))).check(matches(isDisplayed()))
    }
}