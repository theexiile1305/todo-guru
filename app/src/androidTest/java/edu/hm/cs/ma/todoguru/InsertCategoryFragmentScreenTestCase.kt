package edu.hm.cs.ma.todoguru

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class InsertCategoryFragmentScreenTestCase {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun insertCategoryFragmentScreenTestCase() {
        val materialButton = onView(
            allOf(
                withId(R.id.button_skip1), withText("Skip"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment_container),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())

        val actionMenuItemView = onView(
            allOf(
                withId(R.id.manage_categories), withContentDescription("Manage Categories"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.topAppBar),
                        1
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        actionMenuItemView.perform(click())

        val floatingActionButton = onView(
            allOf(
                withId(R.id.createTaskButton),
                childAtPosition(
                    allOf(
                        withId(R.id.tasks_list_container),
                        childAtPosition(
                            withId(R.id.nav_host_fragment_container),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        floatingActionButton.perform(click())

        val appCompatEditText = onView(
            allOf(
                withId(R.id.categoryDescription),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.custom),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatEditText.perform(replaceText("Category"), closeSoftKeyboard())

        val materialButton2 = onView(
            allOf(
                withId(android.R.id.button1), withText("Confirm"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.buttonPanel),
                        0
                    ),
                    3
                )
            )
        )
        materialButton2.perform(scrollTo(), click())

        val textView = onView(
            allOf(
                withId(R.id.title), withText("Category"),
                childAtPosition(
                    allOf(
                        withId(R.id.frame),
                        childAtPosition(
                            withId(R.id.category_list),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textView.check(matches(isDisplayed()))

        val imageView = onView(
            allOf(
                withId(R.id.display_task), withContentDescription("Display task"),
                childAtPosition(
                    allOf(
                        withId(R.id.frame),
                        childAtPosition(
                            withId(R.id.category_list),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        imageView.check(matches(isDisplayed()))
    }
}
