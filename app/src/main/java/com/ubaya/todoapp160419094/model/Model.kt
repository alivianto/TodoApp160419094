package com.ubaya.todoapp160419094.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Todo(
    @ColumnInfo(name="title")
    var title:String,
    @ColumnInfo(name="notes")
    var notes: String,
    @ColumnInfo(name="priority")
    var priority: Int = 3,
    @ColumnInfo(name="is_done")
    var is_done: Int = 0,
    @ColumnInfo(name="todo_date")
    var todo_date: Int = 0
)   {
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}