package com.example.taskscompose.data.repository

import com.example.taskscompose.data.dao.TaskDao
import com.example.taskscompose.data.entity.Tags
import com.example.taskscompose.data.entity.Task
import com.example.taskscompose.data.entity.TaskWithTagList
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class TasksRepository @Inject constructor(
    private val taskDao: TaskDao
) {
    suspend fun insertTask(task: Task) = taskDao.addTask(task)

    suspend fun deleteTask(task: Task) = taskDao.deleteTask(task)

    fun getAllTasks(): Flow<List<Task>> = taskDao.getAllTasks()

    suspend fun insertTag(tag: Tags) = taskDao.upsertTag(tag)

    suspend fun deleteTag(tag: Tags) = taskDao.deleteTag(tag)

    fun getAllTags(): Flow<List<Tags>> = taskDao.getAllTags()

    fun getTagByName(tagName: String): Flow<List<TaskWithTagList>> =
        taskDao.getTasksByTagName(tagName)


    suspend fun insertTagList(tagList: List<Tags>) = taskDao.upsertTagList(tagList)

    fun sortTasksByTag(date: String): Flow<List<Task>> {
        return taskDao.sortByCreationDate(date)
    }
}