package com.example.taskscompose.screens.task

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.taskscompose.components.TaskCard
import com.example.taskscompose.components.WeeklyCalendar
import com.example.taskscompose.data.model.UIState
import com.example.taskscompose.utils.DateUtils


@Composable
fun TasksScreen(
    viewModel: TaskViewModel
) {

    val tasks = viewModel.tasksState.collectAsState()
    LazyColumn(
        modifier = Modifier.fillMaxSize()

    ) {
        item {
            WeeklyCalendar(value = DateUtils.stringToLocalDate(viewModel.selectedDate.value),
                onDateChanged = {
                    viewModel.selectedDate.value = DateUtils.localDateToString(it)
                    viewModel.filterTasksByDate()
                })

        }

        when (val response = tasks.value) {
            is UIState.Success -> {
                items(response.data!!.size) { task ->
                    TaskCard(response.data[task])
                    Spacer(Modifier.height(10.dp))
                }
            }

            is UIState.Error -> {
                item {
                    Text(response.error ?: "Error")
                }
            }

            else -> {}
        }
    }
}
