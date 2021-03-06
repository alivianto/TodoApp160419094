package com.ubaya.todoapp160419094.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.ubaya.todoapp160419094.model.Todo
import com.ubaya.todoapp160419094.model.TodoDatabase
import com.ubaya.todoapp160419094.util.buildDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class TodoListViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {
    val todoLD = MutableLiveData<List<Todo>>()
    val todoLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()

    private var job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    fun refresh() {
        loadingLD.value = true
        todoLoadErrorLD.value = false
        launch {
            val db = buildDb(getApplication())
            todoLD.value = db.todoDao().selectAllUndoneTodo()
        }
    }

    fun clearTask(todo: Todo) {
        launch {
            val db = buildDb(getApplication())
            db.todoDao().deleteTodo(todo)
            todoLD.value = db.todoDao().selectAllTodo()
        }
    }

    fun updateCheckTodo(id: Int){
        launch {
            val db = buildDb(getApplication())
            db.todoDao().updateCheck(id)
            todoLD.value = db.todoDao().selectAllUndoneTodo()
        }
    }
}