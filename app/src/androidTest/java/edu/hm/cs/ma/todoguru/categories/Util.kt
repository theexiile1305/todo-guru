package edu.hm.cs.ma.todoguru.categories

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import edu.hm.cs.ma.todoguru.R
import edu.hm.cs.ma.todoguru.childAtPosition
import edu.hm.cs.ma.todoguru.skipToOnCreateTaskFragment
import org.hamcrest.Matchers

fun skipToSetCategoryDialog() {
    skipToOnCreateTaskFragment()

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
