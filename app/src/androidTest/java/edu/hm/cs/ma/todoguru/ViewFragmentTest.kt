package edu.hm.cs.ma.todoguru


import android.view.View
import android.view.ViewGroup
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
class ViewFragmentTest {

    private val date = "Jun 23, 2020"
    private val date1 = "2020-06-23"
    private val linearLayout = "android.widget.LinearLayout"
    private val scrollView = "android.widget.ScrollView"

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun viewFragmentTest() {
        val materialButton = onView(
            allOf(
                withId(R.id.buttonSkip), withText("Skip"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment_container),
                        0
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())

        val floatingActionButton = onView(
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
        textInputEditText.perform(replaceText("Title"), closeSoftKeyboard())

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
        textInputEditText2.perform(replaceText("Description"), closeSoftKeyboard())

        val textInputEditText3 = onView(
            allOf(
                withId(R.id.dueDate), withText(date),
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

        val materialButton2 = onView(
            allOf(
                withId(android.R.id.button1), withText("OK"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`(scrollView)),
                        0
                    ),
                    3
                )
            )
        )
        materialButton2.perform(scrollTo(), click())

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

        val textInputEditText5 = onView(
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
        textInputEditText5.perform(click())

        val materialButton3 = onView(
            allOf(
                withId(android.R.id.button1), withText("OK"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`(scrollView)),
                        0
                    ),
                    3
                )
            )
        )
        materialButton3.perform(scrollTo(), click())

        val textInputEditText6 = onView(
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
        textInputEditText6.perform(click())

        val materialButton4 = onView(
            allOf(
                withId(android.R.id.button1), withText("OK"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`(scrollView)),
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
                        withClassName(`is`("androidx.coordinatorlayout.widget.CoordinatorLayout")),
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
                        withClassName(`is`("androidx.coordinatorlayout.widget.CoordinatorLayout")),
                        0
                    ),
                    6
                ),
                isDisplayed()
            )
        )
        materialButton6.perform(click())

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

        val textView = onView(
            allOf(
                withId(R.id.title_task), withText("Title"),
                childAtPosition(
                    childAtPosition(
                        IsInstanceOf.instanceOf(android.view.ViewGroup::class.java),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Title")))

        val textView2 = onView(
            allOf(
                withId(R.id.description_task), withText("Description"),
                childAtPosition(
                    childAtPosition(
                        IsInstanceOf.instanceOf(android.view.ViewGroup::class.java),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("Description")))

        val textView3 = onView(
            allOf(
                withId(R.id.dueDate_task), withText(date1),
                childAtPosition(
                    childAtPosition(
                        IsInstanceOf.instanceOf(android.view.ViewGroup::class.java),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        textView3.check(matches(withText(date1)))

        val textView4 = onView(
            allOf(
                withId(R.id.dueDate_task), withText(date1),
                childAtPosition(
                    childAtPosition(
                        IsInstanceOf.instanceOf(android.view.ViewGroup::class.java),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        textView4.check(matches(withText(date1)))

        val materialButton7 = onView(
            allOf(
                withId(R.id.start_button), withText("Start"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`(linearLayout)),
                        4
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialButton7.perform(click())

        val materialButton8 = onView(
            allOf(
                withId(R.id.stop_button), withText("Stop"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`(linearLayout)),
                        4
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        materialButton8.perform(click())

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
