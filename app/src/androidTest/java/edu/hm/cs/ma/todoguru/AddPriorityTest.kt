package edu.hm.cs.ma.todoguru

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
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
        insertTextToField(R.id.estimated, R.id.estimatedTextField, "5")
        setReminderWithDefaultValue()

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
