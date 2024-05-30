package com.example.taskscompose.screens.task

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.taskscompose.components.AddTagsListView
import com.example.taskscompose.components.CustomTextField
import com.example.taskscompose.components.FormCreateButton
import com.example.taskscompose.components.TasksHeaderView
import com.example.taskscompose.navigation.Screens


@Composable
fun AddTaskScreen(
    navController: NavHostController, viewModel: AddTaskViewModel, taskId: Long? = null
) {
    LaunchedEffect(Unit) {
        if (taskId != null) {
            viewModel.getTask(taskId)
        }
    }

    navController.currentBackStackEntry?.savedStateHandle?.getLiveData<String>("selectedDate")
        ?.observe(
            navController.currentBackStackEntry!!
        ) {
            viewModel.date.value = it
        }

    navController.currentBackStackEntry?.savedStateHandle?.getLiveData<String>("timeFrom")?.observe(
        navController.currentBackStackEntry!!
    ) {
        viewModel.timeFrom.value = it
    }

    navController.currentBackStackEntry?.savedStateHandle?.getLiveData<String>("timeTo")?.observe(
        navController.currentBackStackEntry!!
    ) {
        viewModel.timeTo.value = it
    }

    val list = viewModel.allTags.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp)
    ) {

        TasksHeaderView(title = "Add Task") {
            navController.popBackStack()
        }
        Spacer(modifier = Modifier.weight(1f))

        CustomTextField(
            label = "Title", textColor = Color.Gray, value = viewModel.title
        )
        Spacer(modifier = Modifier.weight(1f))


        CustomTextField(
            label = "Date", textColor = Color.Gray, value = viewModel.date
        ) {
            Icon(Icons.Outlined.DateRange,
                contentDescription = "Date Picker",
                modifier = Modifier.clickable {
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        "selectedDate", viewModel.date.value
                    )
                    navController.navigate(Screens.MainApp.DateDialog.route)
                })

        }
        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CustomTextField(
                modifier = Modifier.weight(1f),
                label = "Time From",
                textColor = Color.Gray,
                value = viewModel.timeFrom
            ) {
                Icon(Icons.Default.ExitToApp,
                    contentDescription = "From Time Picker",
                    modifier = Modifier.clickable {
                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            "timeFrom", viewModel.timeFrom.value
                        )
                        navController.navigate(Screens.MainApp.TimeDialog.route + "/timeFrom")
                    })
            }
            CustomTextField(
                modifier = Modifier.weight(1f),
                label = "Time To",
                textColor = Color.Gray,
                value = viewModel.timeTo
            ) {
                Icon(Icons.Default.Done,
                    contentDescription = "From Time Picker",
                    modifier = Modifier.clickable {
                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            "timeTo", viewModel.timeTo.value
                        )
                        navController.navigate(Screens.MainApp.TimeDialog.route + "/timeTo")
                    })
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        CustomTextField(
            label = "Description", textColor = Color.Gray, value = viewModel.description
        )


        Spacer(modifier = Modifier.weight(1f))


        AddTagsListView(list.value, viewModel.selectedTags.value, onTagClick = {
            viewModel.handleSelectedTags(it)
        }) {
            navController.navigate(Screens.MainApp.NewTagDialog.route)
        }
        Spacer(modifier = Modifier.weight(1f))


        FormCreateButton(
            text = if (taskId != null) "Edit Task" else "Add Task",
        ) {
            if (taskId == null) viewModel.addTask()
            else viewModel.editTask(taskId)

            navController.popBackStack()
        }

    }
}

