package edu.hm.cs.ma.todoguru.completed

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withClassName
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
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
class CompletedTaskActionTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun completedTaskActionTest() {
        skipToOnCreateTaskFragment()

        val textInputEditText = onView(
            allOf(
                withId(R.id.title),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.titleTextField),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText.perform(replaceText("as"), closeSoftKeyboard())

        val textInputEditText2 = onView(
            allOf(
                withId(R.id.description),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.descriptionTextField),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText2.perform(replaceText("as"), closeSoftKeyboard())

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

        val textInputEditText3 = onView(
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
        textInputEditText3.perform(click())

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

        val textInputEditText4 = onView(
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
        textInputEditText4.perform(click())

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

        val materialCheckBox = onView(
            allOf(
                withId(R.id.selected_checkbox),
                childAtPosition(
                    allOf(
                        withId(R.id.frame),
                        childAtPosition(
                            withId(R.id.tasks_list),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialCheckBox.perform(click())

        val actionMenuItemView = onView(
            allOf(
                withId(R.id.mark_tasks_as_done), withContentDescription("Done"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.topAppBar),
                        1
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        actionMenuItemView.perform(click())

        val overflowMenuButton2 = onView(
            allOf(
                withContentDescription("More options"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.topAppBar),
                        1
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        overflowMenuButton2.perform(click())

        val materialTextView2 = onView(
            allOf(
                withId(R.id.title), withText("Complete"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.content),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialTextView2.perform(click())

        val linearLayout = onView(
            allOf(
                withId(R.id.frame),
                childAtPosition(
                    allOf(
                        withId(R.id.completed_task_list),
                        childAtPosition(
                            IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java),
                            1
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        linearLayout.check(matches(isDisplayed()))
    }
}
