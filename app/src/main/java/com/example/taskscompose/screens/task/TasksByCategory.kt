package com.example.taskscompose.screens.task


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.taskscompose.components.CustomSearchBar
import com.example.taskscompose.components.TaskCard
import com.example.taskscompose.components.TasksHeaderView

@Composable
fun TasksByCategory(
    navController: NavHostController, taskViewModel: TaskViewModel, category: String?

) {
    LaunchedEffect(Unit) {
        taskViewModel.getTasksByTagName(category.orEmpty())
    }
    val results = taskViewModel.taskWithTags.collectAsState(null)

    val item = results.value

    Column(modifier = Modifier.padding(16.dp)) {
        TasksHeaderView(title = item?.tag?.name ?: "Tag Title") {
            navController.popBackStack()
        }
        if (item != null) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                item {
                    CustomSearchBar(
                        onSearchClick = { taskViewModel.onQueryChangedByTagName(it) },
                        onClearClick = { taskViewModel.onQueryChangedByTagName("") },
                        query = taskViewModel.query.value,
                        onQueryChange = { taskViewModel.onQueryChangedByTagName(it) }
                    )
                }
                items(item.tasks.size) { index ->
                    TaskCard(task = item.tasks[index],
                        tags = listOf(
                            item.tag
                        ),
                        onTaskClick = { taskViewModel.editTask(it, navController) },
                        onDeleteClick = { taskViewModel.deleteTask(it) },
                        onEditClick = { taskViewModel.editTask(it, navController) })
                }
            }
        }
    }
}