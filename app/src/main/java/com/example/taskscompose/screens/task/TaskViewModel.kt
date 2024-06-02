package com.example.taskscompose.screens.task

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.taskscompose.data.entity.TagWithTaskLists
import com.example.taskscompose.data.entity.Tags
import com.example.taskscompose.data.entity.Task
import com.example.taskscompose.data.entity.TaskType
import com.example.taskscompose.data.entity.TaskWithTags
import com.example.taskscompose.data.model.UIState
import com.example.taskscompose.domain.tags.GetAllTagsUseCase
import com.example.taskscompose.domain.tags.InsertTagListUseCase
import com.example.taskscompose.domain.tasks.DeleteTaskUseCase
import com.example.taskscompose.domain.tasks.GetCombineSearchUseCase
import com.example.taskscompose.domain.tasks.GetTasksWithTagNameUseCase
import com.example.taskscompose.navigation.Screens
import com.example.taskscompose.utils.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val getAllTagsUseCase: GetAllTagsUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val insertTagListUseCase: InsertTagListUseCase,
    private val getTasksWithTagNameUseCase: GetTasksWithTagNameUseCase,
    private val getCombineSearchUseCase: GetCombineSearchUseCase
) : ViewModel() {
    init {
        viewModelScope.launch {
            val tagsList = TaskType.entries.map {
                Tags(it.type, it.color, it.icon)
            }
            insertTagListUseCase(tagsList)
            filterTasksByDate()
            getMainTags()
        }
    }

    private val _query = mutableStateOf("")
    val query = _query

    val tasksState: MutableStateFlow<UIState<List<TaskWithTags>>> =
        MutableStateFlow(UIState.Loading())


    val tags = getAllTagsUseCase()
    val cancelledTasks: MutableStateFlow<TagWithTaskLists?> = MutableStateFlow(null)
    val pendingTasks = MutableStateFlow<TagWithTaskLists?>(null)
    val completedTasks = MutableStateFlow<TagWithTaskLists?>(null)
    val onGoingTasks = MutableStateFlow<TagWithTaskLists?>(null)

    val selectedDate = mutableStateOf(DateUtils.localDateToString(LocalDate.now()))
    val currentTag = mutableStateOf("")
    val centerDisplayDate = mutableStateOf(LocalDate.now())
    val taskWithTags = MutableStateFlow<TagWithTaskLists?>(null)

    private fun getCompletedTasks() {
        viewModelScope.launch {
            getTasksWithTagNameUseCase(TaskType.Completed.type).collect {
                completedTasks.value = it
            }
        }
    }

    private fun getOnGoingTasks() {
        viewModelScope.launch {
            getTasksWithTagNameUseCase(TaskType.OnGoing.type).collect {
                onGoingTasks.value = it
            }
        }
    }

    private fun getCancelledTasks() {
        viewModelScope.launch {
            getTasksWithTagNameUseCase(TaskType.Cancelled.type).collect {
                cancelledTasks.value = it
            }
        }
    }

    private fun getPendingTasks() {
        viewModelScope.launch {
            getTasksWithTagNameUseCase(TaskType.Pending.type).collect {
                pendingTasks.value = it
            }
        }
    }


    private fun getMainTags() {
        getCompletedTasks()
        getOnGoingTasks()
        getCancelledTasks()
        getPendingTasks()

    }

    fun filterTasksByDate() {
        sortTasksByDate(selectedDate.value)
    }

    fun sortTasksByDate(date: String) {
        selectedDate.value = date
        combineSearchQuery(query.value, date, "")
    }


    fun getTasksByTagName(tagName: String) {
        currentTag.value = tagName
        combineSearchQuery("", "", tagName)
    }


    fun deleteTask(task: Task) {
        viewModelScope.launch {
            deleteTaskUseCase(task)
        }
    }

    private fun onQueryChanged(query: String) {
        _query.value = query
    }

    fun onQueryChangedByTagName(query: String) {
        onQueryChanged(query)
        combineSearchQuery(query, "", currentTag.value)
    }

    fun onQueryChangedByDate(query: String) {
        onQueryChanged(query)
        combineSearchQuery(query, selectedDate.value, "")
    }

    fun editTask(task: Task, navController: NavHostController) {
        navController.navigate(Screens.MainApp.EditScreen.route + "/${task.taskId}")
    }

    private fun combineSearchQuery(query: String, date: String, tagName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = getCombineSearchUseCase(
                query = query, date = date, tagName = tagName
            )
            Log.d("TaskViewModel", "response: $query $date $tagName")

            launch {
                response.tagWithTasks.collect {
                    taskWithTags.value = it
                    if (taskWithTags.value != null && taskWithTags.value!!.tasks.isNotEmpty()) {
                        val filteredTasks = taskWithTags.value!!.tasks.filter { task ->
                            task.title.contains(query) ||
                                    task.description.contains(query)
                        }
                        taskWithTags.value = taskWithTags.value?.copy(tasks = filteredTasks)
                        Log.d("TaskViewModel", "tagWithTasks: $taskWithTags")

                    }
                }
            }

            launch {
                response.tasksWithTags.collect {
                    tasksState.value = UIState.Success(it)
                    Log.d("TaskViewModel", "tasksState: $it")

                }
            }
        }
    }

}