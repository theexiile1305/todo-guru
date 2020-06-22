package edu.hm.cs.ma.todoguru.explanation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import edu.hm.cs.ma.todoguru.R
import kotlinx.android.synthetic.main.explanation_pages_2.buttonNext
import kotlinx.android.synthetic.main.explanation_pages_2.buttonPrev
import kotlinx.android.synthetic.main.explanation_pages_2.buttonSkip

class ExplanationFragmentTwo : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.explanation_pages_2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonNext.setOnClickListener {
            findNavController()
                .navigate(ExplanationFragmentTwoDirections.actionExplanationFragmentTwoToExplanationFragmentThree())
        }
        buttonPrev.setOnClickListener {
            findNavController()
                .navigate(ExplanationFragmentTwoDirections.actionExplanationFragmentTwoToExplanationFragment())
        }
        buttonSkip.setOnClickListener {
            findNavController()
                .navigate(ExplanationFragmentTwoDirections.actionExplanationFragmentTwoToTaskListFragment())
        }
    }
}
