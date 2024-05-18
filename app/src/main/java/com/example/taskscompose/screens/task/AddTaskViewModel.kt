package com.example.taskscompose.screens.task

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskscompose.data.entity.Tags
import com.example.taskscompose.data.entity.Task
import com.example.taskscompose.data.entity.TaskType
import com.example.taskscompose.domain.tags.InsertTagListUseCase
import com.example.taskscompose.domain.tasks.InsertNewTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val addTaskUseCase: InsertNewTaskUseCase,
    private val insertTagListUseCase: InsertTagListUseCase
) : ViewModel() {

    val title: MutableState<String> = mutableStateOf("")
    val description: MutableState<String> = mutableStateOf("")
    val date: MutableState<String> = mutableStateOf("")
    val timeFrom: MutableState<String> = mutableStateOf("")
    val timeTo: MutableState<String> = mutableStateOf("")
    val type: MutableState<String> = mutableStateOf(TaskType.OnGoing.type)
    val category: MutableState<String> = mutableStateOf("")


    fun addTask() {
        viewModelScope.launch {
            val task = Task(
                title = title.value,
                description = description.value,
                date = date.value,
                timeFrom = timeFrom.value,
                timeTo = timeTo.value,
                type = type.value,
                tagName = category.value
            )
            addTaskUseCase(task)
        }
    }

    fun addTags(list: List<Tags>) {
        viewModelScope.launch {
            insertTagListUseCase(list)
        }
    }
}