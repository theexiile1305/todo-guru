package edu.hm.cs.ma.todoguru.explanation

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import edu.hm.cs.ma.todoguru.R
import edu.hm.cs.ma.todoguru.childAtPosition
import org.hamcrest.Matchers

fun triggerNextButton() {
    val materialButton = Espresso.onView(
        Matchers.allOf(
            ViewMatchers.withId(R.id.buttonNext), ViewMatchers.withText("Next"),
            childAtPosition(
                childAtPosition(
                    ViewMatchers.withId(R.id.nav_host_fragment_container),
                    0
                ),
                2
            ),
            ViewMatchers.isDisplayed()
        )
    )
    materialButton.perform(ViewActions.click())
}

fun triggerSkipButton() {
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

fun triggerPreviousButton() {
    val materialButton = Espresso.onView(
        Matchers.allOf(
            ViewMatchers.withId(R.id.buttonPrev), ViewMatchers.withText("Prev."),
            childAtPosition(
                childAtPosition(
                    ViewMatchers.withId(R.id.nav_host_fragment_container),
                    0
                ),
                3
            ),
            ViewMatchers.isDisplayed()
        )
    )
    materialButton.perform(ViewActions.click())
}
