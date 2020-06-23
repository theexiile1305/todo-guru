package edu.hm.cs.ma.todoguru.repeat

import android.widget.LinearLayout
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
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
import org.hamcrest.Matchers.anything
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class InsertRepeatDialogActionTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityScenarioRule(MainActivity::class.java)

    private val linearLayout = "android.widget.LinearLayout"

    @Test
    fun insertRepeatDialogActionTest() {
        skipToOnCreateTaskFragment()

        val button = onView(
            allOf(
                withId(R.id.chip_set_repeat),
                childAtPosition(
                    childAtPosition(
                        IsInstanceOf.instanceOf(LinearLayout::class.java),
                        5
                    ),
                    3
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
                        withClassName(`is`(linearLayout)),
                        5
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        chip.perform(click())

        val appCompatCheckedTextView = onData(anything())
            .inAdapterView(
                allOf(
                    withId(R.id.select_dialog_listview),
                    childAtPosition(
                        withId(R.id.contentPanel),
                        0
                    )
                )
            )
            .atPosition(0)
        appCompatCheckedTextView.perform(click())

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

        val chip2 = onView(
            allOf(
                withId(R.id.chip_set_repeat), withText("every day"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`(linearLayout)),
                        5
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        chip2.perform(click())

        val materialButton3 = onView(
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
        materialButton3.perform(scrollTo(), click())

        val chip3 = onView(
            allOf(
                withId(R.id.chip_set_repeat), withText("every day"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`(linearLayout)),
                        5
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        chip3.perform(click())

        val appCompatCheckedTextView3 = onData(anything())
            .inAdapterView(
                allOf(
                    withId(R.id.select_dialog_listview),
                    childAtPosition(
                        withId(R.id.contentPanel),
                        0
                    )
                )
            )
            .atPosition(2)
        appCompatCheckedTextView3.perform(click())

        val materialButton4 = onView(
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
        materialButton4.perform(scrollTo(), click())
    }
}
