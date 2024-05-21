package com.example.taskscompose.screens.task

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskscompose.data.entity.Tags
import com.example.taskscompose.data.entity.Task
import com.example.taskscompose.data.entity.TaskType
import com.example.taskscompose.data.model.UIState
import com.example.taskscompose.domain.tags.GetAllTagsUseCase
import com.example.taskscompose.domain.tags.InsertTagListUseCase
import com.example.taskscompose.domain.tasks.GetAllTasksUseCase
import com.example.taskscompose.domain.tasks.InsertNewTaskUseCase
import com.example.taskscompose.domain.tasks.SortTasksByDate
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
    private val insertTaskUseCase: InsertNewTaskUseCase,
    private val insertTagListUseCase: InsertTagListUseCase,
    private val sortTasksUseCase: SortTasksByDate

) : ViewModel() {

    val tasksState: MutableStateFlow<UIState<List<Task>>> = MutableStateFlow(UIState.Loading())
    val selectedDate = mutableStateOf(DateUtils.localDateToString(LocalDate.now()))
    val tags = getAllTagsUseCase()


    fun filterTasksByDate() {
        viewModelScope.launch {
            Log.i("TaskViewModel", "filterTasksByDate: ${selectedDate.value}")
            when (val response = sortTasksUseCase(selectedDate.value)) {
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

    init {
        viewModelScope.launch {
            val tagsList = TaskType.entries.map {
                Tags(it.type, it.color, it.icon)
            }
            insertTagListUseCase(tagsList)
        }
    }
}