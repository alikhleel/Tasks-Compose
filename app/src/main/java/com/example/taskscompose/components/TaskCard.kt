package com.example.taskscompose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskscompose.data.entity.Tags
import com.example.taskscompose.data.entity.Task
import com.example.taskscompose.ui.theme.LightPrimary
import com.example.taskscompose.ui.theme.PrimaryColor
import com.example.taskscompose.utils.ColorUtils
import kotlin.math.min


@Composable
fun TaskCard(
    task: Task,
    modifier: Modifier = Modifier,
    onTaskClick: (Task) -> Unit = {},
    tags: List<Tags>,
    onEditClick: (Task) -> Unit = {},
    onDeleteClick: (Task) -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }
    val color = ColorUtils.stringToColor(
        tags.getOrNull(0)?.color ?: PrimaryColor.toArgb().toString()
    )
    Row(
        modifier = modifier
            .clip(MaterialTheme.shapes.large)
            .background(
                color.copy(alpha = .2f)
            )
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f),
        ) {
            Row(
                modifier = Modifier
            ) {
                Column(modifier = Modifier
                    .drawBehind {
                        drawLine(
                            color = color.copy(alpha = 0.8f),
                            strokeWidth = 10f,
                            start = Offset(0f, 0f),
                            end = Offset(0f, size.height)
                        )
                    }
                    .padding(start = 8.dp)) {
                    Text(
                        task.title,
                        color = PrimaryColor,
                        fontSize = 20.sp,
                        maxLines = 1,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "${task.timeFrom} - ${task.timeTo}", color = LightPrimary, fontSize = 14.sp
                    )

                }
            }
            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                for (tag in tags.subList(0, min(tags.size, 2))) {

                    Box(
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.small)
                            .background(
                                ColorUtils
                                    .stringToColor(tag.color)
                                    .copy(alpha = .2f)
                            )
                            .padding(4.dp)
                    ) {
                        Text(
                            tag.name,
                            color = ColorUtils.stringToColor(tag.color).copy(alpha = 0.9f),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(2.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                }
            }

        }
        Box() {
            IconButton(modifier = Modifier.size(16.dp), onClick = { expanded = !expanded }) {
                Icon(
                    Icons.Outlined.MoreVert, contentDescription = "More Options", tint = Color.Black
                )
            }

            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                Column(
                    modifier = Modifier.background(Color.White)
                ){
                Row {
                    IconButton(onClick = { onEditClick(task) }) {
                        Icon(
                            Icons.Outlined.Edit, contentDescription = "Edit", tint = Color.Black
                        )
                    }
                    Text(text = "Edit")
                }
                    Divider()
                Row {
                    IconButton(onClick = { onDeleteClick(task) }) {
                        Icon(
                            Icons.Outlined.Delete,
                            contentDescription = "Delete",
                            tint = Color.Black
                        )
                        Text(text = "Delete")
                    }}
                }
            }
        }

    }
}