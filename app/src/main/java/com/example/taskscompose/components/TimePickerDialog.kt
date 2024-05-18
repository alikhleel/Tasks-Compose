package com.example.taskscompose.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.taskscompose.utils.DateUtils.convertAndFormatTime
import com.google.android.material.timepicker.TimeFormat

@Composable
fun TimePickerDialog(
    navController: NavHostController, argName: String,
) {
    Column(
        modifier = Modifier
            .clip(MaterialTheme.shapes.large)
            .background(Color.White)
    ) {
        Text(
            text = "Edit Time",
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
        )

        var hour by remember {
            mutableIntStateOf(1)
        }

        var minute by remember {
            mutableIntStateOf(2023)
        }

        var format by remember {
            mutableStateOf("AM")
        }

        BaseDialog(
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
}

