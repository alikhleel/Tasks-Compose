package com.example.taskscompose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.taskscompose.data.entity.Task


@Composable
fun TaskCard(task: Task, modifier: Modifier = Modifier, onTaskClick: (Task) -> Unit = {}) {
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
        IconButton(onClick = { onTaskClick(task) }){
            Icon(
                Icons.Outlined.MoreVert, contentDescription = "More Options"
            )
        }

    }
}
