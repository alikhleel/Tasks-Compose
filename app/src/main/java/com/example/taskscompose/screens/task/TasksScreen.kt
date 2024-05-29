package com.example.taskscompose.screens.task

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskscompose.R
import com.example.taskscompose.components.TaskCard
import com.example.taskscompose.components.WeeklyCalendar
import com.example.taskscompose.data.model.UIState
import com.example.taskscompose.ui.theme.LightGray
import com.example.taskscompose.ui.theme.LightPrimary
import com.example.taskscompose.ui.theme.PrimaryColor
import com.example.taskscompose.utils.DateUtils
import com.kizitonwose.calendar.core.yearMonth


@Composable
fun TasksScreen(
    modifier: Modifier = Modifier, viewModel: TaskViewModel
) {

    val tasks = viewModel.tasksState.collectAsState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)

    ) {

        TextField(modifier = Modifier
            .clip(MaterialTheme.shapes.medium)
            .fillMaxWidth(),
            value = "",
            onValueChange = {},
            colors = TextFieldDefaults.colors(
                focusedContainerColor = LightGray,
                unfocusedContainerColor = LightGray,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),
            leadingIcon = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        Icons.Outlined.Search,
                        contentDescription = "Search",
                        tint = LightPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            },
            placeholder = {
                Text("Search for task")
            },
            trailingIcon = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        Icons.Filled.Clear,
                        contentDescription = "Clear",
                        tint = LightPrimary,
                        modifier = Modifier.size(20.dp)
                    )

                }
            }


        )
        Spacer(Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Tasks",
                color = PrimaryColor,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
            )
            Spacer(Modifier.weight(1f))
            Icon(
                painter = painterResource(id = R.drawable.calendar_month),
                contentDescription = "Calendar",
                tint = LightPrimary,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                "${viewModel.centerDisplayDate.value.yearMonth.month} ${viewModel.centerDisplayDate.value.yearMonth.year}",
                fontWeight = FontWeight.Light,
                fontSize = 14.sp,
                color = LightPrimary
            )
        }
        Spacer(Modifier.height(8.dp))

        WeeklyCalendar(modifier = Modifier.height(100.dp),
            value = DateUtils.stringToLocalDate(viewModel.selectedDate.value),
            onClick = {
                viewModel.selectedDate.value = DateUtils.localDateToString(it)
                viewModel.filterTasksByDate()
            },
            onDisplayDateChange = {
                viewModel.centerDisplayDate.value = it
            })

        LazyColumn {

            when (val response = tasks.value) {
                is UIState.Success -> {
                    items(response.data!!.size) { task ->
                        val data = response.data[task]
                        TaskCard(
                            modifier = Modifier.widthIn(min = 100.dp, max = 200.dp),
                            task = data.task,
                            tags = data.tags,
                        )

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
}
