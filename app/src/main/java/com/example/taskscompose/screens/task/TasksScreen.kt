package com.example.taskscompose.screens.task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskscompose.data.entity.Task


@Composable
fun TasksScreen(
    viewModel: TaskViewModel
) {
    val tasks = viewModel.tasks.collectAsState(listOf())
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red)
    ) {
        items(tasks.value.size) { task ->
            TaskCard(tasks.value[task])
            Spacer(Modifier.height(10.dp))
        }
    }
}

@Composable
fun TaskCard(task: Task) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.large)
            .background(Color.White)
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)

        ) {
            Row(
                modifier = Modifier
                    .padding(8.dp)

            ) {
                Column(modifier = Modifier
                    .drawBehind {
                        drawLine(
                            color = Color.Red,
                            strokeWidth = 2f,
                            start = Offset(0f, 0f),
                            end = Offset(0f, size.height)
                        )
                    }
                    .padding(start = 8.dp)
                ) {
                    Text(task.title)
                    Text("${task.timeFrom} - ${task.timeTo}")

                }
            }

            Row {
                Box(
                    modifier = Modifier
                        .background(Color.Cyan)
                        .padding(4.dp)
                ) {
                    Text(task.tagName)
                }
            }

        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                Icons.Outlined.MoreVert, contentDescription = "More Options"
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTasksScreen() {

    TaskCard(
        Task(1, "Task 1", "Description 1", "", "07:00", "07:15", "", "Tag 1")
    )
}