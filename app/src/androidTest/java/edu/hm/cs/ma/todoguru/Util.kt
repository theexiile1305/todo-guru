package edu.hm.cs.ma.todoguru

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun childAtPosition(
    parentMatcher: Matcher<View>,
    position: Int
): Matcher<View> {

    return object : TypeSafeMatcher<View>() {
        override fun describeTo(description: Description) {
            description.appendText("Child at position $position in parent ")
            parentMatcher.describeTo(description)
        }

        public override fun matchesSafely(view: View): Boolean {
            val parent = view.parent
            return parent is ViewGroup && parentMatcher.matches(parent) && view == parent.getChildAt(
                position
            )
        }
    }
}

fun skipExplanationDialogs() {
    val materialButton = Espresso.onView(
        Matchers.allOf(
            ViewMatchers.withId(R.id.buttonSkip), ViewMatchers.withText("Skip"),
            childAtPosition(
                childAtPosition(
                    ViewMatchers.withId(R.id.nav_host_fragment_container),
                    0
                ),
                4
            ),
            ViewMatchers.isDisplayed()
        )
    )
    materialButton.perform(ViewActions.click())
}

fun skipToOnCreateTaskFragment() {
    skipExplanationDialogs()

    val floatingActionButton = Espresso.onView(
        Matchers.allOf(
            ViewMatchers.withId(R.id.createTaskButton),
            childAtPosition(
                Matchers.allOf(
                    ViewMatchers.withId(R.id.tasks_list_container),
                    childAtPosition(
                        ViewMatchers.withId(R.id.nav_host_fragment_container),
                        0
                    )
                ),
                1
            ),
            ViewMatchers.isDisplayed()
        )
    )
    floatingActionButton.perform(ViewActions.click())
}

fun skipToMoreOptionsMenu() {
    skipExplanationDialogs()

    val overflowMenuButton = Espresso.onView(
        Matchers.allOf(
            ViewMatchers.withContentDescription("More options"),
            childAtPosition(
                childAtPosition(
                    ViewMatchers.withId(R.id.topAppBar),
                    1
                ),
                2
            ),
            ViewMatchers.isDisplayed()
        )
    )
    overflowMenuButton.perform(ViewActions.click())
}

fun date(date: String) = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"))

fun insertTextToField(field: Int, fieldValue: Int, string: String) {
    val textInputEditText = Espresso.onView(
        Matchers.allOf(
            ViewMatchers.withId(field),
            childAtPosition(
                childAtPosition(
                    ViewMatchers.withId(fieldValue),
                    0
                ),
                0
            ),
            ViewMatchers.isDisplayed()
        )
    )
    textInputEditText.perform(ViewActions.replaceText(string), ViewActions.closeSoftKeyboard())
}

fun setReminderWithDefaultValue() {
    val chip = Espresso.onView(
        Matchers.allOf(
            ViewMatchers.withId(R.id.chipSetReminder), ViewMatchers.withText("Set Reminder"),
            childAtPosition(
                childAtPosition(
                    ViewMatchers.withClassName(Matchers.`is`("android.widget.LinearLayout")),
                    5
                ),
                0
            ),
            ViewMatchers.isDisplayed()
        )
    )
    chip.perform(ViewActions.click())

    val textInputEditText3 = Espresso.onView(
        Matchers.allOf(
            ViewMatchers.withId(R.id.reminderDate),
            childAtPosition(
                childAtPosition(
                    ViewMatchers.withId(R.id.reminderDateTextField),
                    0
                ),
                0
            ),
            ViewMatchers.isDisplayed()
        )
    )
    textInputEditText3.perform(ViewActions.click())

    val materialButton2 = Espresso.onView(
        Matchers.allOf(
            ViewMatchers.withId(android.R.id.button1), ViewMatchers.withText("OK"),
            childAtPosition(
                childAtPosition(
                    ViewMatchers.withClassName(Matchers.`is`("android.widget.ScrollView")),
                    0
                ),
                3
            )
        )
    )
    materialButton2.perform(ViewActions.scrollTo(), ViewActions.click())

    val textInputEditText4 = Espresso.onView(
        Matchers.allOf(
            ViewMatchers.withId(R.id.reminderTime),
            childAtPosition(
                childAtPosition(
                    ViewMatchers.withId(R.id.reminderTimeTextField),
                    0
                ),
                0
            ),
            ViewMatchers.isDisplayed()
        )
    )
    textInputEditText4.perform(ViewActions.click())

    val materialButton3 = Espresso.onView(
        Matchers.allOf(
            ViewMatchers.withId(android.R.id.button1), ViewMatchers.withText("OK"),
            childAtPosition(
                childAtPosition(
                    ViewMatchers.withClassName(Matchers.`is`("android.widget.ScrollView")),
                    0
                ),
                3
            )
        )
    )
    materialButton3.perform(ViewActions.scrollTo(), ViewActions.click())

    val materialButton4 = Espresso.onView(
        Matchers.allOf(
            ViewMatchers.withId(R.id.setReminderButton), ViewMatchers.withText("Create"),
            childAtPosition(
                childAtPosition(
                    ViewMatchers.withClassName(Matchers.`is`("androidx.coordinatorlayout.widget.CoordinatorLayout")),
                    0
                ),
                3
            ),
            ViewMatchers.isDisplayed()
        )
    )
    materialButton4.perform(ViewActions.click())
}
