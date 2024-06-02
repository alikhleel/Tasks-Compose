package com.example.taskscompose.data.entity

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Done
import androidx.compose.ui.graphics.toArgb
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.taskscompose.getIconName
import com.example.taskscompose.ui.theme.LightBlue
import com.example.taskscompose.ui.theme.LightGreen
import com.example.taskscompose.ui.theme.LightPurple
import com.example.taskscompose.ui.theme.LightRed
import kotlinx.coroutines.flow.Flow


@Entity(tableName = "tasks_table")
data class Task(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "task_Id") var taskId: Long? = null,
    @ColumnInfo(name = "task_title") val title: String,
    @ColumnInfo(name = "task_description") val description: String,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "time_from") val timeFrom: String,
    @ColumnInfo(name = "time_to") val timeTo: String,
    @ColumnInfo(name = "task_type") val type: String,
    @ColumnInfo(name = "task_tag_name") val tagName: String = "",
)

data class TaskWithTagsCombine(
    val tasksWithTags: Flow<List<TaskWithTags>>,
    val tagWithTasks: Flow<TagWithTaskLists>
)

enum class TaskType(val type: String, val color: String, val icon: String) {
    Pending("Pending", LightPurple.toArgb().toString(), getIconName(Icons.Outlined.DateRange)),
    OnGoing("OnGoing", LightGreen.toArgb().toString(), getIconName(Icons.Outlined.Build)),
    Cancelled("Cancelled", LightRed.toArgb().toString(), getIconName(Icons.Outlined.Delete)),
    Completed("Completed", LightBlue.toArgb().toString(), getIconName(Icons.Outlined.Done)),
}