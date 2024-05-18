package com.example.taskscompose.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.taskscompose.data.entity.Tags
import com.example.taskscompose.data.entity.Task
import com.example.taskscompose.data.entity.TaskWithTagList
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Upsert
    suspend fun addTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("SELECT * FROM tasks_table")
    fun getAllTasks(): Flow<List<Task>>

    @Upsert
    suspend fun upsertTag(tag: Tags)

    @Delete
    suspend fun deleteTag(tag: Tags)

    @Query("SELECT * FROM tags_table")
    fun getAllTags(): Flow<List<Tags>>

    @Query("SELECT * FROM tags_table WHERE tag_name = :tagName")
    fun getTasksByTagName(tagName: String): Flow<List<TaskWithTagList>>

    @Upsert
    suspend fun upsertTagList(tagList: List<Tags>)

    @Query("SELECT * FROM tasks_table WHERE date = :date")
    fun sortByCreationDate(date: String): Flow<List<Task>>

}