package edu.hm.cs.ma.todoguru

import edu.hm.cs.ma.todoguru.categories.InsertCategoryActionTest
import edu.hm.cs.ma.todoguru.categories.InsertCategoryCancelButtonTest
import edu.hm.cs.ma.todoguru.categories.InsertCategoryConfirmButtonTest
import edu.hm.cs.ma.todoguru.categories.InsertCategoryFragmentScreenTest
import edu.hm.cs.ma.todoguru.explanation.ExplanationNextButtonTest
import edu.hm.cs.ma.todoguru.explanation.ExplanationOneSkipButtonTest
import edu.hm.cs.ma.todoguru.explanation.ExplanationPreviousButtonTest
import edu.hm.cs.ma.todoguru.explanation.ExplanationThreeSkipButtonTest
import edu.hm.cs.ma.todoguru.explanation.ExplanationTwoSkipButtonTest
import edu.hm.cs.ma.todoguru.repeat.InsertRepeatDialogActionTest
import edu.hm.cs.ma.todoguru.subtask.InsertSubTaskActionTest
import edu.hm.cs.ma.todoguru.subtask.InsertSubTaskFragmentScreenTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    InsertCategoryActionTest::class,
    InsertCategoryCancelButtonTest::class,
    InsertCategoryConfirmButtonTest::class,
    InsertCategoryFragmentScreenTest::class,
    ExplanationNextButtonTest::class,
    ExplanationOneSkipButtonTest::class,
    ExplanationPreviousButtonTest::class,
    ExplanationThreeSkipButtonTest::class,
    ExplanationTwoSkipButtonTest::class,
    InsertRepeatDialogActionTest::class,
    InsertSubTaskActionTest::class,
    InsertSubTaskFragmentScreenTest::class,
    InsertTaskDialogScreenTestCase::class,
    MainScreenTestCase::class,
    SmokeTest::class,
    AddPriorityTest::class,
    ToDoGuruDatabaseTest::class,
    RecommendAppTest::class,
    ContactDevelopersTest::class
)
class AndroidTestSuite
