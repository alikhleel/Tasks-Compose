package com.example.taskscompose.components

import android.util.Log
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.taskscompose.data.model.Time
import com.example.taskscompose.utils.DateUtils
import com.example.taskscompose.utils.DateUtils.convertAndFormatTime
import com.google.android.material.timepicker.TimeFormat

@Composable
fun TimePickerDialog(
    navController: NavHostController, argName: String,
) {
    var hour by remember {
        mutableIntStateOf(1)
    }

    var minute by remember {
        mutableIntStateOf(2023)
    }

    var format by remember {
        mutableStateOf("AM")
    }
    LaunchedEffect(true) {
        navController.previousBackStackEntry?.savedStateHandle?.get<String>(
            argName
        ).apply {
            var time: String? = this
            if (this.isNullOrEmpty()) {
                time = "${DateUtils.getCurrentHour()}:${DateUtils.getCurrentMinute()}"
            }
            val splitTime: List<String> = time!!.split(":")
            Log.i("TimePickerDialog", "Time: $splitTime")
            hour = splitTime[0].toInt()
            minute = splitTime[1].toInt()
            if (hour > 12) {
                format = "PM"
                hour -= 12
            } else {
                format = "AM"
            }

        }
    }




    BaseDialog(
        title = "Select Time",
        onConfirm = {
            val time = convertAndFormatTime(hour, minute, format)
            navController.previousBackStackEntry?.savedStateHandle?.set(
                argName, time
            )
            navController.popBackStack()
        },
        onDismiss = {
            navController.popBackStack()
        },
    ) {
        WheelTimePicker(
            startTime = Time(hour, minute),
            modifier = Modifier.width(200.dp),
            offset = 3,
            timeFormat = TimeFormat.CLOCK_12H,
            textSize = 32
        ) { h, m, f ->
            hour = h
            minute = m
            format = f ?: "AM"
        }
    }
}

