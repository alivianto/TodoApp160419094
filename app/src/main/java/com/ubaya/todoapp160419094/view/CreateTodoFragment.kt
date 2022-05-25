package com.ubaya.todoapp160419094.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
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
import java.util.concurrent.TimeUnit

/**
 * A simple [Fragment] subclass.
 * Use the [CreateTodoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreateTodoFragment : Fragment(), TodoAddListener, RadioButtonListener, TodoDateListener {

    private lateinit var viewModel: DetailTodoViewModel
    private lateinit var dataBinding: FragmentCreateTodoBinding

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
        dataBinding.todo?.let{
            val list = listOf(it)
            viewModel.addTodo(list)
            Toast.makeText(v.context, "Todo Created", Toast.LENGTH_SHORT).show()
            Navigation.findNavController(v).popBackStack()
            val myWorkRequest = OneTimeWorkRequestBuilder<TodoWorker>()
                .setInitialDelay(5, TimeUnit.SECONDS)
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
        TODO("Not yet implemented")
    }

    override fun onTimeClick(view: View) {
        TODO("Not yet implemented")
    }
}