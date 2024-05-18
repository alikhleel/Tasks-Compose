package com.example.taskscompose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.taskscompose.ui.theme.PrimaryColor
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun DatePickerDialog(
    navController: NavHostController,
) {
    var selectedDate by remember {
        mutableStateOf<LocalDate?>(navController.previousBackStackEntry?.savedStateHandle?.get<String>(
            "selectedDate"
        )?.let {
            LocalDate.parse(it)
        })
    }

    val currentMonth = remember {
        if (selectedDate != null) {
            YearMonth.from(selectedDate)
        } else {
            YearMonth.now()
        }
    }
    val startMonth = remember { currentMonth.minusMonths(100) } // Adjust as needed
    val endMonth = remember { currentMonth.plusMonths(100) } // Adjust as needed
    val daysOfWeek = daysOfWeek(firstDayOfWeek = firstDayOfWeekFromLocale())

    val scope = rememberCoroutineScope()

    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = daysOfWeek.first()
    )
    Column(
        modifier = Modifier
            .clip(MaterialTheme.shapes.large)
            .background(Color.White)
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                "${state.firstVisibleMonth.yearMonth.month} ${state.firstVisibleMonth.yearMonth.year}",
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = {
                scope.launch {
                    state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.minusMonths(1))
                }
            }) {
                Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "Previous Month")
            }

            IconButton(onClick = {
                scope.launch {
                    state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.plusMonths(1))
                }
            }) {
                Icon(Icons.Default.KeyboardArrowRight, contentDescription = "Next Month")
            }
        }
        Column(modifier = Modifier.height(300.dp)) {
            DaysOfWeekTitle(daysOfWeek = daysOfWeek)
            HorizontalCalendar(state = state, dayContent = { day ->
                Day(
                    day, isSelected = selectedDate == day.date
                ) {
                    selectedDate = if (selectedDate == day.date) null else day.date
                }
            })
        }


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(modifier = Modifier.padding(horizontal = 16.dp),
                shape = MaterialTheme.shapes.medium,
                onClick = {
                    navController.popBackStack()
                }) {
                Text(
                    "Cancel"
                )
            }
            Button(modifier = Modifier.padding(horizontal = 16.dp),
                shape = MaterialTheme.shapes.medium,
                onClick = {
                    if (selectedDate == null) return@Button
                    navController.previousBackStackEntry?.savedStateHandle?.set(
                        "selectedDate", selectedDate.toString()
                    )
                    navController.popBackStack()
                }) {
                Text(
                    "Save"
                )
            }
        }
    }
}

@Composable
fun DaysOfWeekTitle(daysOfWeek: List<DayOfWeek>) {
    Row(modifier = Modifier.fillMaxWidth()) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
            )
        }
    }
}


@Composable
fun Day(day: CalendarDay, isSelected: Boolean, onClick: (CalendarDay) -> Unit) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clip(CircleShape)
            .background(color = if (isSelected) PrimaryColor else Color.Transparent)
            .clickable(enabled = day.position == DayPosition.MonthDate, onClick = { onClick(day) }),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (day.position == DayPosition.MonthDate) day.date.dayOfMonth.toString() else "",
            color = if (isSelected) Color.White else Color.Black
        )
    }
}
