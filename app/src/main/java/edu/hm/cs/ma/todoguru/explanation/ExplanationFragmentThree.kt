package edu.hm.cs.ma.todoguru.explanation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import edu.hm.cs.ma.todoguru.R
import kotlinx.android.synthetic.main.explanation_pages_3.buttonNext
import kotlinx.android.synthetic.main.explanation_pages_3.buttonPrev
import kotlinx.android.synthetic.main.explanation_pages_3.buttonSkip
import timber.log.Timber

class ExplanationFragmentThree : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.explanation_pages_3, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonNext.setOnClickListener {
            findNavController()
                .navigate(ExplanationFragmentThreeDirections.actionExplanationFragmentThreeToTaskListFragment())
            Timber.d("Go to TaskListFragment")
        }
        buttonPrev.setOnClickListener {
            findNavController()
                .navigate(ExplanationFragmentThreeDirections.actionExplanationFragmentThreeToExplanationFragmentTwo())
            Timber.d("Go back to second explanationFragment")
        }
        buttonSkip.setOnClickListener {
            findNavController()
                .navigate(ExplanationFragmentThreeDirections.actionExplanationFragmentThreeToTaskListFragment())
            Timber.d("Go to TaskListFragment")
        }
    }
}
