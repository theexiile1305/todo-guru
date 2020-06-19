package edu.hm.cs.ma.todoguru.explanation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import edu.hm.cs.ma.todoguru.R

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
        view.findViewById<Button>(R.id.button_next3).setOnClickListener {
            findNavController().navigate(ExplanationFragmentThreeDirections.actionExplanationFragmentThreeToTaskListFragment())
        }
        view.findViewById<Button>(R.id.button_prev3).setOnClickListener {
            findNavController().navigate(ExplanationFragmentThreeDirections.actionExplanationFragmentThreeToExplanationFragmentTwo())
        }
        view.findViewById<Button>(R.id.button_skip3).setOnClickListener {
            findNavController().navigate(ExplanationFragmentThreeDirections.actionExplanationFragmentThreeToTaskListFragment())
        }
    }
}
