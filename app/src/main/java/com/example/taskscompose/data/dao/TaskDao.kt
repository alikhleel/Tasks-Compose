package com.example.taskscompose.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.taskscompose.data.entity.Task

@Dao
interface TaskDao {
    @Insert
    fun insert(task: Task)

    @Delete
    fun delete(task: Task)

    @Query("SELECT * FROM tags_table")
    fun getAll(): List<Task>
}