package edu.hm.cs.ma.todoguru

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.pressImeActionButton
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withClassName
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
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
class ProductivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityScenarioRule(MainActivity::class.java)

    private val widgetScrollView = "android.widget.ScrollView"

    private val widgetCoordinator = "androidx.coordinatorlayout.widget.CoordinatorLayout"

    @Test
    fun productivityTest() {
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
        textInputEditText.perform(replaceText("task1"), closeSoftKeyboard())

        val textInputEditText2 = onView(
            allOf(
                withId(R.id.title), withText("task1"),
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
        textInputEditText2.perform(click())

        val textInputEditText3 = onView(
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
        textInputEditText3.perform(replaceText("j"), closeSoftKeyboard())

        val textInputEditText4 = onView(
            allOf(
                withId(R.id.dueDate), withText("Jun 25, 2020"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.dueDateTextField),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText4.perform(click())

        val materialButton4 = onView(
            allOf(
                withId(android.R.id.button1), withText("OK"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`(widgetScrollView)),
                        0
                    ),
                    3
                )
            )
        )
        materialButton4.perform(scrollTo(), click())

        val textInputEditText5 = onView(
            allOf(
                withId(R.id.description), withText("j"),
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
        textInputEditText5.perform(pressImeActionButton())

        val textInputEditText6 = onView(
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
        textInputEditText6.perform(replaceText("1"), closeSoftKeyboard())

        val textInputEditText7 = onView(
            allOf(
                withId(R.id.estimated), withText("1"),
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
        textInputEditText7.perform(pressImeActionButton())

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

        val textInputEditText8 = onView(
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
        textInputEditText8.perform(click())

        val materialButton5 = onView(
            allOf(
                withId(android.R.id.button1), withText("OK"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`(widgetScrollView)),
                        0
                    ),
                    3
                )
            )
        )
        materialButton5.perform(scrollTo(), click())

        val textInputEditText9 = onView(
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
        textInputEditText9.perform(click())

        val materialButton6 = onView(
            allOf(
                withId(android.R.id.button1), withText("OK"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`(widgetScrollView)),
                        0
                    ),
                    3
                )
            )
        )
        materialButton6.perform(scrollTo(), click())

        val materialButton7 = onView(
            allOf(
                withId(R.id.setReminderButton), withText("Create"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`(widgetCoordinator)),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        materialButton7.perform(click())

        val materialButton8 = onView(
            allOf(
                withId(R.id.insertTaskButton), withText("Create"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`(widgetCoordinator)),
                        0
                    ),
                    6
                ),
                isDisplayed()
            )
        )
        materialButton8.perform(click())

        val floatingActionButton2 = onView(
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
        floatingActionButton2.perform(click())

        val textInputEditText10 = onView(
            allOf(
                withId(R.id.title), withText("task1"),
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
        textInputEditText10.perform(replaceText("task2"))

        val textInputEditText11 = onView(
            allOf(
                withId(R.id.title), withText("task2"),
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
        textInputEditText11.perform(closeSoftKeyboard())

        val textInputEditText12 = onView(
            allOf(
                withId(R.id.title), withText("task2"),
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
        textInputEditText12.perform(pressImeActionButton())

        val textInputEditText13 = onView(
            allOf(
                withId(R.id.description), withText("j"),
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
        textInputEditText13.perform(pressImeActionButton())

        val textInputEditText14 = onView(
            allOf(
                withId(R.id.estimated), withText("1"),
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
        textInputEditText14.perform(pressImeActionButton())

        val materialButton9 = onView(
            allOf(
                withId(R.id.insertTaskButton), withText("Create"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`(widgetCoordinator)),
                        0
                    ),
                    6
                ),
                isDisplayed()
            )
        )
        materialButton9.perform(click())

        val materialCheckBox = onView(
            allOf(
                withId(R.id.selected_checkbox),
                childAtPosition(
                    allOf(
                        withId(R.id.frame),
                        childAtPosition(
                            withId(R.id.tasks_list),
                            1
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

        val floatingActionButton3 = onView(
            allOf(
                withId(R.id.viewProductivityButton),
                childAtPosition(
                    allOf(
                        withId(R.id.tasks_list_container),
                        childAtPosition(
                            withId(R.id.nav_host_fragment_container),
                            0
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        floatingActionButton3.perform(click())

        val materialCheckBox2 = onView(
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
        materialCheckBox2.perform(click())
    }
}
