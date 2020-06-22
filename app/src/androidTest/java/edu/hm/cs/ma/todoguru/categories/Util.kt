package edu.hm.cs.ma.todoguru.categories

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import edu.hm.cs.ma.todoguru.R
import edu.hm.cs.ma.todoguru.childAtPosition
import edu.hm.cs.ma.todoguru.skipExplanationDialogs
import org.hamcrest.Matchers

fun skipToSetCategoryDialog() {
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

    val chip = Espresso.onView(
        Matchers.allOf(
            ViewMatchers.withId(R.id.chip_set_category), ViewMatchers.withText("Set Category"),
            childAtPosition(
                childAtPosition(
                    ViewMatchers.withClassName(Matchers.`is`("android.widget.LinearLayout")),
                    5
                ),
                1
            ),
            ViewMatchers.isDisplayed()
        )
    )
    chip.perform(ViewActions.click())
}
