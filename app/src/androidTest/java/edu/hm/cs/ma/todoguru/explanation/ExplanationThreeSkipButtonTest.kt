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
class ExplanationThreeSkipButtonTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun explanationThreeSkipButtonTest() {
        triggerNextButton(2)

        triggerNextButton(2)

        triggerSkipButton(5)
    }
}
