package edu.hm.cs.ma.todoguru.productivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Pie
import edu.hm.cs.ma.todoguru.R
import edu.hm.cs.ma.todoguru.database.ToDoGuruDatabase
import timber.log.Timber

class ProductivityFragment : Fragment() {

    private lateinit var pie: Pie
    private lateinit var viewModel: ProductivityViewModel
    var data: MutableList<DataEntry> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.productivity_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = requireActivity().run {
            val dataSource = ToDoGuruDatabase.getInstance(this).taskDatabaseDao
            val viewModelFactory = ProductivityViewModel.Factory(dataSource, application)
            ViewModelProvider(this, viewModelFactory).get(ProductivityViewModel::class.java)
        }
        val anyChartView: AnyChartView = view.findViewById(R.id.any_chart_view)

        viewModel.doneTasks.observe(
            viewLifecycleOwner,
            Observer {
                putDataInPieChart(anyChartView, "Completed", it.size)
                Timber.d("Task completed visualized with a circle: %s", it.size)
            }
        )

        viewModel.todoTasks.observe(
            viewLifecycleOwner,
            Observer {
                putDataInPieChart(anyChartView, "To-Do", it.size)
                Timber.d("Uncompleted tasks visualized with a circle: %s", it.size)
            }
        )
    }

    private fun putDataInPieChart(anyChartView: AnyChartView, title: String, value: Int) {
        pie = AnyChart.pie()
        data.add(ValueDataEntry(title, value))
        pie.data(data)
        anyChartView.setChart(pie)
    }
}
