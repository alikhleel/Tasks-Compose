package com.example.taskscompose.screens.task

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskscompose.data.entity.Tags
import com.example.taskscompose.domain.tags.GetAllTagsUseCase
import com.example.taskscompose.domain.tags.InsertNewTagUseCase
import com.example.taskscompose.domain.tasks.GetAllTasksUseCase
import com.example.taskscompose.domain.tasks.GetTasksWithTagUseCase
import com.example.taskscompose.domain.tasks.InsertNewTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val getAllTasksUseCase: GetAllTasksUseCase,
    private val getAllTagsUseCase: GetAllTagsUseCase,
    private val getTasksByTagsUseCase: GetTasksWithTagUseCase,
    private val insertTaskUseCase: InsertNewTaskUseCase,
    private val insertTagUseCase: InsertNewTagUseCase

) : ViewModel() {

    val tasks = getAllTasksUseCase()
    val tags = getAllTagsUseCase()
    val tasksByTag = getTasksByTagsUseCase("Personal")

    init {
        viewModelScope.launch {
            insertTagUseCase(Tags("Work", Color.Yellow.value.toString(), "Border"))
            insertTagUseCase(Tags("Personal", Color.Red.value.toString(), "Border"))
            insertTagUseCase(Tags("School", Color.Blue.value.toString(), "Border"))
            insertTagUseCase(Tags("Home", Color.Green.value.toString(), "Border"))
        }

    }
}