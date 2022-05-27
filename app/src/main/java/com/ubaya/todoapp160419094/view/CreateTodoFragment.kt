package com.ubaya.todoapp160419094.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.RadioButton
import android.widget.TimePicker
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.ubaya.todoapp160419094.R
import com.ubaya.todoapp160419094.databinding.FragmentCreateTodoBinding
import com.ubaya.todoapp160419094.model.Todo
import com.ubaya.todoapp160419094.util.NotificationHelper
import com.ubaya.todoapp160419094.util.TodoWorker
import com.ubaya.todoapp160419094.viewmodel.DetailTodoViewModel
import kotlinx.android.synthetic.main.fragment_create_todo.*
import kotlinx.android.synthetic.main.fragment_create_todo.view.*
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.min

/**
 * A simple [Fragment] subclass.
 * Use the [CreateTodoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreateTodoFragment : Fragment(), TodoAddListener, RadioButtonListener, TodoDateListener,
    DatePickerDialog.OnDateSetListener , TimePickerDialog.OnTimeSetListener {

    private lateinit var viewModel: DetailTodoViewModel
    private lateinit var dataBinding: FragmentCreateTodoBinding
    // Untuk perhitungan waktu tunda notifikasi
    var year = 0
    var month = 0
    var day = 0
    var hour = 0
    var minute = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = FragmentCreateTodoBinding.inflate(inflater, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(DetailTodoViewModel::class.java)

        //instantiate layout variabel
        dataBinding.todo = Todo("","")
        dataBinding.radioListener = this
        dataBinding.addListener = this
        dataBinding.dateListener = this

        /*
        buttonAdd.setOnClickListener {
            var radio = view.findViewById<RadioButton>(radioGroupPriority.checkedRadioButtonId)
            val title = editTitle.text.toString()
            val notes = editNotes.text.toString()
            var todo = Todo(editTitle.text.toString(), editNotes.text.toString(), radio.tag.toString().toInt())
            var list = listOf(todo)
            viewModel.addTodo(list)
            Toast.makeText(view.context, "Data added", Toast.LENGTH_SHORT).show()
            Navigation.findNavController(it).popBackStack()
            //NotificationHelper(view.context).createNotification("Todo $title Created","A new todo has been created!\n$notes\nStay focus!")

            // Membuat work request
            val myWorkRequest = OneTimeWorkRequestBuilder<TodoWorker>()
                .setInitialDelay(5, TimeUnit.SECONDS)
                .setInputData(workDataOf(
                    "title" to "Todo $title Created",
                    "message" to "A new todo has been created. With notes: $notes.Stay Focus"
                ))
                .build()
            WorkManager.getInstance(requireContext()).enqueue(myWorkRequest)
        }
        */
    }

    override fun onAddNewTodo(v: View) {
        // Hitung selisih waktu sekarang dengan tenggat
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day, hour, minute, 0)
        val today = Calendar.getInstance()
        val diff = (calendar.timeInMillis / 1000L) - (today.timeInMillis / 1000L)

        dataBinding.todo?.let{
            it.todo_date = (calendar.timeInMillis / 1000L).toInt()
            val list = listOf(it)
            viewModel.addTodo(list)
            Toast.makeText(v.context, "Todo Created", Toast.LENGTH_SHORT).show()
            Navigation.findNavController(v).popBackStack()
            val myWorkRequest = OneTimeWorkRequestBuilder<TodoWorker>()
                .setInitialDelay(diff, TimeUnit.SECONDS)
                .setInputData(workDataOf(
                    "title" to "Todo ${it.title} Created",
                    "message" to "A new todo has been created. Stay Focus!"
                ))
                .build()
            WorkManager.getInstance(requireContext()).enqueue(myWorkRequest)
        }

    }

    override fun onRadioClick(view: View, priority: Int, obj: Todo) {
        obj.priority = priority
    }

    override fun onDateClick(view: View) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        var day = calendar.get(Calendar.DAY_OF_MONTH)
        activity?.let{
            DatePickerDialog(it, this, year, month, day).show()
        }
    }

    override fun onTimeClick(view: View) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        TimePickerDialog(activity, this, hour, minute, DateFormat.is24HourFormat(activity)).show()
    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
        Calendar.getInstance().let {
            it.set(year, month, day)
            val date = day.toString().padStart(2,'0') + '-' +
                    month.toString().padStart(2,'0') + '-' +
                    year.toString()
            dataBinding.root.editDate.setText(date)
            this.year = year
            this.month = month
            this.day = day
        }
    }

    override fun onTimeSet(p0: TimePicker?, hourOfDay: Int, minute: Int) {
        val time = hourOfDay.toString().padStart(2, '0') + ':' +
                minute.toString().padStart(2, '0')
        dataBinding.root.editTime.setText(time)
        this.hour = hourOfDay
        this.minute = minute
    }
}