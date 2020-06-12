package edu.hm.cs.ma.todoguru

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.ContentView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import edu.hm.cs.ma.todoguru.database.Task
import edu.hm.cs.ma.todoguru.notification.ReminderNotification
import kotlinx.android.synthetic.main.explanation_pages.*
import kotlinx.android.synthetic.main.explanation_pages.topAppBar
import kotlinx.android.synthetic.main.insert_task_dialog.*
import kotlinx.android.synthetic.main.task_view_holder.view.*

class ExplanationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.explanation_pages, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.button_next).setOnClickListener {
            findNavController().navigate(ExplanationFragmentDirections.actionExplanationDialogToExplanationDialogTwo())
        }
        view.findViewById<Button>(R.id.button_skip).setOnClickListener {
            findNavController().navigate(ExplanationFragmentDirections.actionExplanationDialogToTaskListFragment2())
        }

    }
}
