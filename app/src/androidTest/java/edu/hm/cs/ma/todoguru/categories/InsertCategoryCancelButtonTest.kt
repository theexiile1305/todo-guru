package edu.hm.cs.ma.todoguru.categories

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import edu.hm.cs.ma.todoguru.MainActivity
import edu.hm.cs.ma.todoguru.R
import edu.hm.cs.ma.todoguru.childAtPosition
import org.hamcrest.Matchers
import org.hamcrest.Matchers.allOf
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class InsertCategoryCancelButtonTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun insertCategoryCancelButtonTest() {
        skipToSetCategoryDialog()

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

        val button = onView(
            allOf(
                withId(R.id.chip_set_category),
                childAtPosition(
                    childAtPosition(
                        IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java),
                        5
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        button.check(matches(isDisplayed()))

        val chip = onView(
            allOf(
                withId(R.id.chip_set_category), withText("Set Category"),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withClassName(Matchers.`is`("android.widget.LinearLayout")),
                        5
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        chip.perform(click())

        val materialButton2 = onView(
            allOf(
                withId(android.R.id.button2), withText("Cancel"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.buttonPanel),
                        0
                    ),
                    2
                )
            )
        )
        materialButton2.perform(scrollTo(), click())

        val button2 = onView(
            allOf(
                withId(R.id.chip_set_category),
                childAtPosition(
                    childAtPosition(
                        IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java),
                        5
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        button2.check(matches(isDisplayed()))
    }
}
