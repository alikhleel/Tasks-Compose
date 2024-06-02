package com.example.taskscompose.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.taskscompose.data.entity.TagWithTaskLists
import com.example.taskscompose.data.entity.Tags
import com.example.taskscompose.data.entity.Task
import com.example.taskscompose.data.entity.TaskTagCrossRef
import com.example.taskscompose.data.entity.TaskWithTags
import com.example.taskscompose.data.entity.TaskWithTagsCombine
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Transaction
    @Upsert
    suspend fun addTask(task: Task): Long

    @Transaction
    @Upsert
    suspend fun insertTaskWithTags(task: Task, tags: List<Tags>)

    @Transaction
    @Upsert
    suspend fun insertTaskTagCrossRefs(taskTagCrossRef: List<TaskTagCrossRef>)

    @Transaction
    @Delete
    suspend fun deleteTaskTagCrossRefs(taskTagCrossRef: List<TaskTagCrossRef>)

    @Transaction
    @Delete
    suspend fun deleteTask(task: Task)

    @Query("SELECT * FROM tasks_table")
    fun getAllTasks(): Flow<List<Task>>

    @Transaction
    @Upsert
    suspend fun upsertTag(tag: Tags)

    @Transaction
    @Delete
    suspend fun deleteTag(tag: Tags)

    @Transaction
    @Query("SELECT * FROM tags_table")
    fun getAllTags(): Flow<List<Tags>>

    @Transaction
    @Upsert
    suspend fun upsertTagList(tagList: List<Tags>)

    @Transaction
    @Query("SELECT * FROM tags_table WHERE tag_name = :tagName")
    fun getTagsWithTask(tagName: String): Flow<TagWithTaskLists>

    @Transaction
    @Query("SELECT * FROM tasks_table WHERE date = :date")
    fun sortTasksByCreationDate(date: String): Flow<List<Task>>

    @Transaction
    @Query("SELECT * FROM tasks_table WHERE date = :date And (task_title LIKE '%' || :query || '%' OR task_description LIKE '%' || :query || '%')")
    fun getTasksWithTagsByDate(date: String, query: String): Flow<List<TaskWithTags>>

    @Transaction
    fun getCombineSearch(tagName: String, date: String, query: String): TaskWithTagsCombine {
        val tagsWithTasks = getTagsWithTask(tagName)
        val tasksWithTagsByDate = getTasksWithTagsByDate(date, query)
        return TaskWithTagsCombine(
            tagWithTasks = tagsWithTasks, tasksWithTags = tasksWithTagsByDate
        )
    }

    @Transaction
    @Query("SELECT * FROM tags_table")
    fun getTagsWithTasks(): Flow<List<TagWithTaskLists>>

    @Transaction
    @Query("SELECT * FROM tasks_table WHERE task_Id = :taskId")
    suspend fun getTasksWithTags(taskId: Long): TaskWithTags

}