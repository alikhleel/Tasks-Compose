package com.example.taskscompose.data.repository

import com.example.taskscompose.data.dao.TaskDao
import com.example.taskscompose.data.entity.TagWithTaskLists
import com.example.taskscompose.data.entity.Tags
import com.example.taskscompose.data.entity.Task
import com.example.taskscompose.data.entity.TaskTagCrossRef
import com.example.taskscompose.data.entity.TaskWithTags
import com.example.taskscompose.data.model.UIState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class TasksRepository @Inject constructor(
    private val taskDao: TaskDao
) {
    suspend fun insertTask(task: Task): Long = taskDao.addTask(task)

    suspend fun deleteTask(task: Task) = taskDao.deleteTask(task)

    fun getAllTasks(): UIState<Flow<List<Task>>> {
        return try {
            val tasks = taskDao.getAllTasks()
            UIState.Success(tasks)
        } catch (e: Exception) {
            UIState.Error(e.message.toString())
        }
    }

    suspend fun insertTaskTagCrossRefs(taskTagCrossRefs: List<TaskTagCrossRef>) {
        taskDao.insertTaskTagCrossRefs(taskTagCrossRefs)
    }

    suspend fun deleteTaskTagCrossRefs(taskTagCrossRef: List<TaskTagCrossRef>) {
        taskDao.deleteTaskTagCrossRefs(taskTagCrossRef)
    }

    suspend fun insertTag(tag: Tags) = taskDao.upsertTag(tag)

    suspend fun deleteTag(tag: Tags) = taskDao.deleteTag(tag)

    fun getAllTags(): Flow<List<Tags>> = taskDao.getAllTags()


    suspend fun insertTagList(tagList: List<Tags>) = taskDao.upsertTagList(tagList)

    fun sortTasksByTag(date: String): UIState<Flow<List<Task>>> {
        return try {
            val tasks = taskDao.sortTasksByCreationDate(date)
            UIState.Success(tasks)
        } catch (e: Exception) {
            UIState.Error(e.message.toString())
        }
    }

    fun getTagsWithTask(tagName: String): Flow<TagWithTaskLists> {
        return taskDao.getTagsWithTask(tagName)
    }

    fun getTasksWithTagsByDate(date: String): UIState<Flow<List<TaskWithTags>>> {
        return try {
            val tasks = taskDao.getTasksWithTagsByDate(date)
            UIState.Success(tasks)
        } catch (e: Exception) {
            UIState.Error(e.message.toString())
        }
    }

    suspend fun getTasksWithTags(taskId: Long): TaskWithTags {
        return taskDao.getTasksWithTags(taskId)
    }


    fun getTagWithTaskLists() = taskDao.getTagsWithTasks()

}