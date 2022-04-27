package com.ubaya.todoapp160419094.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.ubaya.todoapp160419094.R
import com.ubaya.todoapp160419094.databinding.FragmentEditTodoBinding
import com.ubaya.todoapp160419094.model.Todo
import com.ubaya.todoapp160419094.viewmodel.DetailTodoViewModel
import kotlinx.android.synthetic.main.fragment_create_todo.*
import kotlinx.android.synthetic.main.fragment_create_todo.textJudul
import kotlinx.android.synthetic.main.fragment_edit_todo.*

/**
 * A simple [Fragment] subclass.
 * Use the [EditTodoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditTodoFragment : Fragment(), RadioButtonListener, TodoSaveChangesListener {

    private lateinit var viewModel: DetailTodoViewModel
    private lateinit var dataBinding: FragmentEditTodoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_edit_todo,  container, false)
        // OR
        // dataBinding = FragmentEditTodoBinding.inflate(inflater, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailTodoViewModel::class.java)
        val uuid = EditTodoFragmentArgs.fromBundle(requireArguments()).uuid
        viewModel.fetch(uuid)
        observeViewModel()

//        textJudul.text = "Edit Todo"
//        buttonSave.text = "Save Changes"

        /*
        buttonAdd.setOnClickListener {
            var radio = view.findViewById<RadioButton>(radioGroupPriority.checkedRadioButtonId)
            viewModel.update(
                uuid,
                editTitle.text.toString(),
                editNotes.text.toString(),
                radio.tag.toString().toInt()
            )
            Toast.makeText(view.context, "Todo updated", Toast.LENGTH_SHORT).show()
            Navigation.findNavController(it).popBackStack()
        }

         */

        // Instatiate Listener
        dataBinding.radioListener = this
        dataBinding.saveListener = this
    }

    private fun observeViewModel() {
        viewModel.todoLD.observe(viewLifecycleOwner) {
            dataBinding.todo = it
            /*
            editTitle.setText(it.title)
            editNotes.setText(it.notes)
            when(it.priority) {
                1-> radioLow.isChecked = true
                2-> radioMedium.isChecked = true
                else -> radioHigh.isChecked = true
            }

             */
        }
    }

    override fun onRadioClick(view: View, priority: Int, obj: Todo) {
        obj.priority = priority
    }

    override fun onSaveChangeClick(view: View, obj: Todo) {
        viewModel.update(obj)
        Toast.makeText(view.context, "Todo updated", Toast.LENGTH_SHORT).show()
        Navigation.findNavController(view).popBackStack()
    }
}