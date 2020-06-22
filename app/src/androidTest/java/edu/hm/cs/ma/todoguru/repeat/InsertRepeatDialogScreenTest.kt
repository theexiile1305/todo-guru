package edu.hm.cs.ma.todoguru.repeat

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withClassName
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import edu.hm.cs.ma.todoguru.MainActivity
import edu.hm.cs.ma.todoguru.R
import edu.hm.cs.ma.todoguru.childAtPosition
import edu.hm.cs.ma.todoguru.skipToOnCreateTaskFragment
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class InsertRepeatDialogScreenTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun insertRepeatDialogScreenTest() {
        skipToOnCreateTaskFragment()

        val viewGroup = onView(
            allOf(
                childAtPosition(
                    childAtPosition(
                        IsInstanceOf.instanceOf(android.view.ViewGroup::class.java),
                        0
                    ),
                    5
                ),
                isDisplayed()
            )
        )
        viewGroup.check(matches(isDisplayed()))

        val button = onView(
            allOf(
                withId(R.id.chip_set_repeat),
                childAtPosition(
                    childAtPosition(
                        IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java),
                        5
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        button.check(matches(isDisplayed()))

        val chip = onView(
            allOf(
                withId(R.id.chip_set_repeat), withText("Set task on repeat"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        5
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        chip.perform(click())

        val linearLayout = onView(
            allOf(
                withId(R.id.topPanel),
                childAtPosition(
                    allOf(
                        withId(R.id.parentPanel),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        linearLayout.check(matches(isDisplayed()))

        val frameLayout = onView(
            allOf(
                withId(R.id.contentPanel),
                childAtPosition(
                    allOf(
                        withId(R.id.parentPanel),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        frameLayout.check(matches(isDisplayed()))
    }
}
