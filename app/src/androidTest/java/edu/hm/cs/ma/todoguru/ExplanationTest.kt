package edu.hm.cs.ma.todoguru

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class ExplanationTest {
    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)
    @Test
    fun explanationTest() {
        val materialButton = onView(
            allOf(withId(R.id.button_next1), withText("Next"),
                childAtPosition(childAtPosition(withId(R.id.nav_host_fragment_container), 0), 1), isDisplayed()))
        materialButton.perform(click())

        val materialButton2 = onView(
            allOf(
                withId(R.id.button_prev2), withText("Prev."),
                childAtPosition(childAtPosition(withId(R.id.nav_host_fragment_container), 0), 2), isDisplayed()))
        materialButton2.perform(click())

        val materialButton3 = onView(
            allOf(
                withId(R.id.button_next1), withText("Next"),
                childAtPosition(childAtPosition(withId(R.id.nav_host_fragment_container), 0), 1), isDisplayed()))
        materialButton3.perform(click())

        val materialButton4 = onView(
            allOf(
                withId(R.id.button_next2), withText("Next"),
                childAtPosition(childAtPosition(withId(R.id.nav_host_fragment_container), 0), 1), isDisplayed()))
        materialButton4.perform(click())

        val materialButton5 = onView(
            allOf(
                withId(R.id.button_skip3), withText("Skip"),
                childAtPosition(childAtPosition(withId(R.id.nav_host_fragment_container), 0), 3), isDisplayed()))
        materialButton5.perform(click())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent) && view == parent.getChildAt(position)
            }
        }
    }
}
