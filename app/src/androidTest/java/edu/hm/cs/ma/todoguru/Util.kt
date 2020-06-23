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

fun date(date: String) = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
