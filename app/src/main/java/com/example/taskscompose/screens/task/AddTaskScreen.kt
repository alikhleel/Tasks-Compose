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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.taskscompose.components.AddTagsListView
import com.example.taskscompose.components.CustomTextField
import com.example.taskscompose.components.FormCreateButton
import com.example.taskscompose.components.TasksHeaderView
import com.example.taskscompose.data.entity.Tags
import com.example.taskscompose.navigation.Screens


@Composable
fun AddTaskScreen(
    navController: NavHostController, viewModel: AddTaskViewModel
) {
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

    val list = listOf(
        Tags("Home", Color.Red.toArgb().toString(), ""),
        Tags("Work", Color.Green.toArgb().toString(), ""),
        Tags("School", Color.Blue.toArgb().toString(), ""),
        Tags("Farm", Color.Yellow.toArgb().toString(), ""),
    )

    LaunchedEffect(Unit) {
        viewModel.addTags(list)
    }

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
                        navController.navigate(Screens.MainApp.TimeDialog.route + "/timeTo")
                    })
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        CustomTextField(
            label = "Description", textColor = Color.Gray, value = viewModel.description
        )


        Spacer(modifier = Modifier.weight(1f))


        AddTagsListView(list, viewModel.category.value) {
            viewModel.category.value = it.name
        }
        Spacer(modifier = Modifier.weight(1f))


        FormCreateButton() {
            viewModel.addTask()
        }

    }
}

