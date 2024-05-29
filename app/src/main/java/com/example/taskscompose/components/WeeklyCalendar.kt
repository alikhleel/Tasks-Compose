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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.taskscompose.ui.theme.PrimaryColor
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.kizitonwose.calendar.core.atStartOfMonth
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.kizitonwose.calendar.core.yearMonth
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale


@Composable
fun WeeklyCalendar(
    modifier: Modifier = Modifier,
    value: LocalDate,
    onClick: (LocalDate) -> Unit = {},
    onDisplayDateChange: (LocalDate) -> Unit = {},
) {

    val currentDate by remember { mutableStateOf(value) }
    val currentMonth = remember { currentDate.yearMonth }
    val startDate = remember { currentMonth.minusMonths(100).atStartOfMonth() } // Adjust as needed
    val endDate = remember { currentMonth.plusMonths(100).atEndOfMonth() } // Adjust as needed
    val firstDayOfWeek = remember { firstDayOfWeekFromLocale() } // Available from the library


    val state = rememberWeekCalendarState(
        startDate = startDate,
        endDate = endDate,
        firstVisibleWeekDate = currentDate,
        firstDayOfWeek = firstDayOfWeek
    )

    LaunchedEffect(state.firstVisibleWeek.days[3].date) {
        onDisplayDateChange(state.firstVisibleWeek.days[3].date)
    }

    Row(
        modifier = modifier
            .fillMaxWidth()

    ) {

        WeekCalendar(
            state = state,
            dayContent = { day ->
                Day(
                    day = day.date, isSelected = value == day.date, onDateSelected = onClick
                )

            })
    }

}

@Composable
fun Day(
    day: LocalDate, isSelected: Boolean = false, onDateSelected: (LocalDate) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .aspectRatio(
                1f / 2f
            )
            .clip(MaterialTheme.shapes.medium)
            .background(
                color = if (isSelected) PrimaryColor else Color.White,
            )
            .clickable {
                onDateSelected(day)
            },
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = day.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
                    .substring(0, 2), color = if (isSelected) Color.White else Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = day.dayOfMonth.toString(),
                color = if (isSelected) Color.White else Color.Black
            )
        }
    }
}
