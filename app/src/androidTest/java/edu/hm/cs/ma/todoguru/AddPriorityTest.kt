package edu.hm.cs.ma.todoguru

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withClassName
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class AddPriorityTest {
    private val linearLayout = "android.widget.LinearLayout"

    @Rule
    @JvmField
    var mActivityTestRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun addPriorityTest() {
        skipToOnCreateTaskFragment()

        insertTextToField(R.id.title, R.id.titleTextField, "A")
        insertTextToField(R.id.description, R.id.descriptionTextField, "B")

        val textInputEditText3 = onView(
            allOf(
                withId(R.id.estimated),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.estimatedTextField),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText3.perform(replaceText("5"), closeSoftKeyboard())

        val chip = onView(
            allOf(
                withId(R.id.chipSetReminder), withText("Set Reminder"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`(linearLayout)),
                        5
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        chip.perform(click())

        val textInputEditText4 = onView(
            allOf(
                withId(R.id.reminderDate),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.reminderDateTextField),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText4.perform(click())

        val materialButton2 = onView(
            allOf(
                withId(android.R.id.button1), withText("OK"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    3
                )
            )
        )
        materialButton2.perform(scrollTo(), click())

        val textInputEditText5 = onView(
            allOf(
                withId(R.id.reminderTime),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.reminderTimeTextField),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText5.perform(click())

        val materialButton3 = onView(
            allOf(
                withId(android.R.id.button1), withText("OK"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    3
                )
            )
        )
        materialButton3.perform(scrollTo(), click())

        val materialButton4 = onView(
            allOf(
                withId(R.id.setReminderButton), withText("Create"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("androidx.coordinatorlayout.widget.CoordinatorLayout")),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        materialButton4.perform(click())

        val switch = onView(
            allOf(
                withId(R.id.switch_button),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`(linearLayout)),
                        6
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        switch.perform(click())

        val materialButton5 = onView(
            allOf(
                withId(R.id.insertTaskButton), withText("Create"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("androidx.coordinatorlayout.widget.CoordinatorLayout")),
                        0
                    ),
                    7
                ),
                isDisplayed()
            )
        )
        materialButton5.perform(click())

        val recyclerView = onView(
            allOf(
                withId(R.id.tasks_list),
                childAtPosition(
                    withClassName(`is`(linearLayout)),
                    1
                )
            )
        )
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(0, click()))
    }
}
