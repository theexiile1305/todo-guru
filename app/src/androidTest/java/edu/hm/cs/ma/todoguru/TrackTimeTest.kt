package edu.hm.cs.ma.todoguru


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.pressImeActionButton
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withClassName
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith



@LargeTest
@RunWith(AndroidJUnit4::class)
class TrackTimeTest {
    val linear = "android.widget.LinearLayout"

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun trackTimeTest() {
        val floatingActionButton = onView(
            allOf(
                withId(R.id.fab),
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
        textInputEditText.perform(replaceText("T"), closeSoftKeyboard())

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
        textInputEditText2.perform(replaceText("D"), closeSoftKeyboard())

        val textInputEditText3 = onView(
            allOf(
                withId(R.id.dueDate), withText("Jun 22, 2020"),
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
        textInputEditText3.perform(click())

        val materialButton = onView(
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
        materialButton.perform(scrollTo(), click())

        val textInputEditText4 = onView(
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
        textInputEditText4.perform(replaceText("5"), closeSoftKeyboard())

        val textInputEditText5 = onView(
            allOf(
                withId(R.id.estimated), withText("5"),
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
        textInputEditText5.perform(pressImeActionButton())

        val textInputEditText6 = onView(
            allOf(
                withId(R.id.reminder_date), withText("Jun 22, 2020"),
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
        textInputEditText6.perform(click())

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

        val materialButton3 = onView(
            allOf(
                withId(R.id.button_create), withText("Create"),
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
        materialButton3.perform(click())

        val appCompatImageView = onView(
            allOf(
                withId(R.id.display_task), withContentDescription("Display task"),
                childAtPosition(
                    allOf(
                        withId(R.id.frame),
                        childAtPosition(
                            withId(R.id.tasks_list),
                            0
                        )
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        appCompatImageView.perform(click())

        val materialTextView = onView(
            allOf(
                withId(android.R.id.title), withText("View Task"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialTextView.perform(click())

        val materialButton4 = onView(
            allOf(
                withId(R.id.start_button), withText("Start"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`(this.linear)),
                        4
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialButton4.perform(click())

        val materialButton5 = onView(
            allOf(
                withId(R.id.stop_button), withText("Stop"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`(this.linear)),
                        4
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        materialButton5.perform(click())

        val materialButton6 = onView(
            allOf(
                withId(R.id.start_button), withText("Start"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`(this.linear)),
                        4
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialButton6.perform(click())

        val materialButton7 = onView(
            allOf(
                withId(R.id.stop_button), withText("Stop"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`(this.linear)),
                        4
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        materialButton7.perform(click())

        val button = onView(
            allOf(
                withId(R.id.start_button),
                childAtPosition(
                    childAtPosition(
                        IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java),
                        4
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        button.check(matches(isDisplayed()))

        val button2 = onView(
            allOf(
                withId(R.id.stop_button),
                childAtPosition(
                    childAtPosition(
                        IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java),
                        4
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        button2.check(matches(isDisplayed()))

        val textView = onView(
            allOf(
                withId(R.id.textview),
                childAtPosition(
                    childAtPosition(
                        IsInstanceOf.instanceOf(android.view.ViewGroup::class.java),
                        2
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textView.check(matches(isDisplayed()))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
