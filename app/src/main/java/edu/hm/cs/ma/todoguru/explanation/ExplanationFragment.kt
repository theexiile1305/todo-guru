package edu.hm.cs.ma.todoguru.explanation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import edu.hm.cs.ma.todoguru.R
import kotlinx.android.synthetic.main.explanation_pages.*
import timber.log.Timber

class ExplanationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        Timber.d("Go to first explanationFragment")
        return inflater.inflate(R.layout.explanation_pages, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonNext.setOnClickListener {
            findNavController()
                .navigate(ExplanationFragmentDirections.actionExplanationFragmentToExplanationFragmentTwo())
            Timber.d("Change to second explanationFragment")
        }
        buttonSkip.setOnClickListener {
            findNavController()
                .navigate(ExplanationFragmentDirections.actionExplanationFragmentToTaskListFragment())
            Timber.d("Skip explanationFragments and go straight to tasklistfragment")
        }
    }
}
