package com.example.taskscompose.screens.task

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskscompose.data.entity.Tags
import com.example.taskscompose.data.entity.Task
import com.example.taskscompose.data.entity.TaskTagCrossRef
import com.example.taskscompose.data.entity.TaskType
import com.example.taskscompose.data.repository.TasksRepository
import com.example.taskscompose.domain.tags.InsertTagListUseCase
import com.example.taskscompose.domain.tasks.InsertNewTaskUseCase
import com.example.taskscompose.utils.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject


@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val repository: TasksRepository,
    private val addTaskUseCase: InsertNewTaskUseCase,
    private val insertTagListUseCase: InsertTagListUseCase
) : ViewModel() {
    val taskId: MutableState<Int?> = mutableStateOf(null)

    val title: MutableState<String> = mutableStateOf("")
    val description: MutableState<String> = mutableStateOf("")
    val date: MutableState<String> = mutableStateOf(DateUtils.localDateToString(LocalDate.now()))
    val timeFrom: MutableState<String> =
        mutableStateOf(DateUtils.formatTimeStamp(DateUtils.getCurrentTime()))
    val timeTo: MutableState<String> = mutableStateOf("")
    val type: MutableState<String> = mutableStateOf(TaskType.OnGoing.type)
    val category: MutableState<String> = mutableStateOf("")

    val allTags = repository.getAllTags()
    val selectedTags = mutableStateOf<Set<Tags>>(emptySet())
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
            insertTasksWithTags(
                task, selectedTags.value.toList()
            )
        }
    }

    fun addTags(
        tag: Tags
    ) {
        viewModelScope.launch {
            repository.insertTag(tag)
        }
    }

    private suspend fun insertTasksWithTags(task: Task, tags: List<Tags>) {
        val taskId = repository.insertTask(task)
        val taskTagCrossRefs = tags.map {
            TaskTagCrossRef(taskId, it.name)
        }
        repository.insertTaskTagCrossRefs(taskTagCrossRefs)
    }

    fun handleSelectedTags(item: Tags) {
        selectedTags.value = if (selectedTags.value.contains(item)) selectedTags.value.minus(item)
        else selectedTags.value.plus(item)
    }

    suspend fun getTask(taskId: Long) {
        val task = repository.getTasksWithTags(taskId)
        title.value = task.task.title
        description.value = task.task.description
        date.value = task.task.date
        timeFrom.value = task.task.timeFrom
        timeTo.value = task.task.timeTo
        selectedTags.value = task.tags.toSet()
    }

    fun editTask(taskId: Long) {
        viewModelScope.launch {
            val task = Task(
                taskId = taskId,
                title = title.value,
                description = description.value,
                date = date.value,
                timeFrom = timeFrom.value,
                timeTo = timeTo.value,
                type = type.value,
                tagName = category.value
            )
            val tags = selectedTags.value.toList()

            insertTasksWithTags(task, tags)
        }
    }

}