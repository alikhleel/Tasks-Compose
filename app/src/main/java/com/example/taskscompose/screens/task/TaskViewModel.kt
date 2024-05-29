package com.example.taskscompose.screens.task

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskscompose.data.entity.TagWithTaskLists
import com.example.taskscompose.data.entity.Tags
import com.example.taskscompose.data.entity.TaskType
import com.example.taskscompose.data.entity.TaskWithTags
import com.example.taskscompose.data.model.UIState
import com.example.taskscompose.domain.tags.GetAllTagsUseCase
import com.example.taskscompose.domain.tags.InsertTagListUseCase
import com.example.taskscompose.domain.tasks.GetAllTagWithTasksUseCase
import com.example.taskscompose.domain.tasks.GetAllTasksUseCase
import com.example.taskscompose.domain.tasks.GetTasksWithTagByDateUseCase
import com.example.taskscompose.domain.tasks.GetTasksWithTagNameUseCase
import com.example.taskscompose.domain.tasks.InsertNewTaskUseCase
import com.example.taskscompose.utils.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val getAllTasksUseCase: GetAllTasksUseCase,
    private val getAllTagsUseCase: GetAllTagsUseCase,
    private val getAllTagWithTasksUseCase: GetAllTagWithTasksUseCase,
    private val insertTaskUseCase: InsertNewTaskUseCase,
    private val insertTagListUseCase: InsertTagListUseCase,
    private val getTasksWithTagByDateUseCase: GetTasksWithTagByDateUseCase,
    private val getTasksWithTagNameUseCase: GetTasksWithTagNameUseCase

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

    val tasksState: MutableStateFlow<UIState<List<TaskWithTags>>> =
        MutableStateFlow(UIState.Loading())


    val tags = getAllTagsUseCase()
    val cancelledTasks: MutableStateFlow<TagWithTaskLists?> = MutableStateFlow(null)
    val pendingTasks = MutableStateFlow<TagWithTaskLists?>(null)
    val completedTasks = MutableStateFlow<TagWithTaskLists?>(null)
    val onGoingTasks = MutableStateFlow<TagWithTaskLists?>(null)

    val selectedDate = mutableStateOf(DateUtils.localDateToString(LocalDate.now()))

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
        getTasksWithTagsByDate(selectedDate.value)
    }

    fun sortTasksByDate(date: String) {
        getTasksWithTagsByDate(date)
    }

    private fun getTasksWithTagsByDate(date: String) {
        viewModelScope.launch {
            when (val response = getTasksWithTagByDateUseCase(date)) {
                is UIState.Success -> {
                    response.data!!.collect {
                        tasksState.value = UIState.Success(it)
                    }
                }

                is UIState.Error -> tasksState.value = UIState.Error(response.error)
                is UIState.Empty -> tasksState.value = UIState.Empty(title = response.title)
                is UIState.Loading -> tasksState.value = UIState.Loading()
            }
        }
    }

    suspend fun getTasksByTagName(tagName: String) {
        getTasksWithTagNameUseCase(tagName).collect {
            taskWithTags.value = it
        }

    }
}