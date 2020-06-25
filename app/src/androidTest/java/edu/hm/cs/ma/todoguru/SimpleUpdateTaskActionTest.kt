package edu.hm.cs.ma.todoguru

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.pressImeActionButton
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
class SimpleUpdateTaskActionTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityScenarioRule(MainActivity::class.java)

    private val coordinatorLayout = "androidx.coordinatorlayout.widget.CoordinatorLayout"

    @Test
    fun simpleUpdateTaskActionTest() {
        skipToOnCreateTaskFragment()

        replaceText(R.id.title, R.id.titleTextField, "Title")

        replaceText(R.id.description, R.id.descriptionTextField, "Description")

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
        textInputEditText3.perform(replaceText("00"), closeSoftKeyboard())

        val textInputEditText4 = onView(
            allOf(
                withId(R.id.estimated), withText("00"),
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
        textInputEditText4.perform(pressImeActionButton())

        val textInputEditText5 = onView(
            allOf(
                withId(R.id.estimated), withText("00"),
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
        textInputEditText5.perform(replaceText("0"))

        val textInputEditText6 = onView(
            allOf(
                withId(R.id.estimated), withText("0"),
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
        textInputEditText6.perform(closeSoftKeyboard())

        val textInputEditText7 = onView(
            allOf(
                withId(R.id.estimated), withText("0"),
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
        textInputEditText7.perform(click())

        val textInputEditText8 = onView(
            allOf(
                withId(R.id.estimated), withText("0"),
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
        textInputEditText8.perform(pressImeActionButton())

        val materialButton2 = onView(
            allOf(
                withId(R.id.insertTaskButton), withText("Create"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`(coordinatorLayout)),
                        0
                    ),
                    7
                ),
                isDisplayed()
            )
        )
        materialButton2.perform(click())

        val chip = onView(
            allOf(
                withId(R.id.chipSetReminder), withText("Set Reminder"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        5
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        chip.perform(click())

        val textInputEditText9 = onView(
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
        textInputEditText9.perform(click())

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

        val textInputEditText10 = onView(
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
        textInputEditText10.perform(click())

        val materialButton4 = onView(
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
        materialButton4.perform(scrollTo(), click())

        val materialButton5 = onView(
            allOf(
                withId(R.id.setReminderButton), withText("Create"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`(coordinatorLayout)),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        materialButton5.perform(click())

        val materialButton6 = onView(
            allOf(
                withId(R.id.insertTaskButton), withText("Create"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`(coordinatorLayout)),
                        0
                    ),
                    7
                ),
                isDisplayed()
            )
        )
        materialButton6.perform(click())

        val recyclerView = onView(
            allOf(
                withId(R.id.tasks_list),
                childAtPosition(
                    withClassName(`is`("android.widget.LinearLayout")),
                    1
                )
            )
        )
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        val materialButton7 = onView(
            allOf(
                withId(R.id.updateTaskButton), withText("Update"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`(coordinatorLayout)),
                        0
                    ),
                    7
                ),
                isDisplayed()
            )
        )
        materialButton7.perform(click())
    }

    private fun replaceText(
        field: Int,
        textField: Int,
        stringToBeSet: String
    ) {
        val textInputEditText = onView(
            allOf(
                withId(field),
                childAtPosition(
                    childAtPosition(
                        withId(textField),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText.perform(replaceText(stringToBeSet), closeSoftKeyboard())
    }
}
