package edu.hm.cs.ma.todoguru.task.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import edu.hm.cs.ma.todoguru.R
import edu.hm.cs.ma.todoguru.task.ViewTaskFragmentArgs
import kotlinx.android.synthetic.main.view_task_fragment.description_task
import kotlinx.android.synthetic.main.view_task_fragment.dueDate_task
import kotlinx.android.synthetic.main.view_task_fragment.title_task

class ViewTaskFragment : Fragment() {
    private val args: ViewTaskFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.view_task_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val task = args.task

        title_task.text = task.title
        description_task.text = task.description
        dueDate_task.text = task.dueDate.toString()
    }
}
