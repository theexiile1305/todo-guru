package edu.hm.cs.ma.todoguru.explanation

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import edu.hm.cs.ma.todoguru.R
import edu.hm.cs.ma.todoguru.childAtPosition
import org.hamcrest.Matchers

fun triggerNextButton(position: Int) {
    val materialButton = Espresso.onView(
        Matchers.allOf(
            ViewMatchers.withId(R.id.buttonNext), ViewMatchers.withText("Next"),
            childAtPosition(
                childAtPosition(
                    ViewMatchers.withId(R.id.nav_host_fragment_container),
                    0
                ),
                position
            ),
            ViewMatchers.isDisplayed()
        )
    )
    materialButton.perform(ViewActions.click())
}

fun triggerSkipButton(position: Int) {
    val materialButton = Espresso.onView(
        Matchers.allOf(
            ViewMatchers.withId(R.id.buttonSkip), ViewMatchers.withText("Skip"),
            childAtPosition(
                childAtPosition(
                    ViewMatchers.withId(R.id.nav_host_fragment_container),
                    0
                ),
                position
            ),
            ViewMatchers.isDisplayed()
        )
    )
    materialButton.perform(ViewActions.click())
}

fun triggerPreviousButton(position: Int) {
    val materialButton = Espresso.onView(
        Matchers.allOf(
            ViewMatchers.withId(R.id.buttonPrev), ViewMatchers.withText("Prev."),
            childAtPosition(
                childAtPosition(
                    ViewMatchers.withId(R.id.nav_host_fragment_container),
                    0
                ),
                position
            ),
            ViewMatchers.isDisplayed()
        )
    )
    materialButton.perform(ViewActions.click())
}
