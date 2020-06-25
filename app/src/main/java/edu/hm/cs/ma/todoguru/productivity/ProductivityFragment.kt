package edu.hm.cs.ma.todoguru.productivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
//import edu.hm.cs.ma.todoguru.databinding.ProductivityFragmentBinding
import com.anychart.charts.Pie

import edu.hm.cs.ma.todoguru.R
import edu.hm.cs.ma.todoguru.database.ToDoGuruDatabase


class ProductivityFragment : Fragment() {

    private lateinit var pie : Pie
   // private lateinit var binding: ProductivityFragmentBinding
    private lateinit var viewModel: ProductivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        //binding = DataBindingUtil.inflate(inflater, R.layout.productivity_fragment, container, false)
        return inflater.inflate(R.layout.productivity_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = requireActivity().run {
            val dataSource = ToDoGuruDatabase.getInstance(this).taskDatabaseDao
            val viewModelFactory = ProductivityViewModel.Factory(dataSource, application)
            ViewModelProvider(this, viewModelFactory).get(ProductivityViewModel::class.java)
        }
        
        var doneTasks = viewModel.doneTasks.size
        var todoTasks = viewModel.todoTasks.size
        
        var anyChartView : AnyChartView = view.findViewById(R.id.any_chart_view)
        pie = AnyChart.pie()
        var data : MutableList<DataEntry> = arrayListOf()
        data.add(ValueDataEntry("Completed", doneTasks))
        data.add(ValueDataEntry("To-Do", todoTasks))
        pie.data(data)


        anyChartView.setChart(pie)

    }
}
