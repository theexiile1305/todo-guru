package edu.hm.cs.ma.todoguru.explanation

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import edu.hm.cs.ma.todoguru.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class ExplanationPreviousButtonTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun explanationPreviousButtonTest() {
        triggerPreviousButton()

        triggerNextButton()

        triggerPreviousButton()

        triggerNextButton()

        triggerNextButton()

        triggerPreviousButton()

        triggerPreviousButton()
    }
}
