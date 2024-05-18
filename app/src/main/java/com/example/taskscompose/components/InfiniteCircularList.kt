package com.example.taskscompose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskscompose.components.wheelview.SelectorOptions
import com.example.taskscompose.components.wheelview.WheelView
import com.example.taskscompose.data.model.Time
import com.example.taskscompose.utils.DateUtils
import com.google.android.material.timepicker.TimeFormat


@Composable
fun WheelTimePicker(
    modifier: Modifier = Modifier,
    offset: Int = 4,
    selectorEffectEnabled: Boolean = true,
    timeFormat: Int = TimeFormat.CLOCK_24H,
    startTime: Time = Time(DateUtils.getCurrentHour(), DateUtils.getCurrentMinute()),
    textSize: Int = 16,
    onTimeChanged: (Int, Int, String?) -> Unit = { _, _, _ -> },
) {

    var selectedTime by remember { mutableStateOf(startTime) }

    val formats = listOf<String>("AM", "PM")


    val hours = mutableListOf<Int>().apply {
        for (hour in 1..if (timeFormat == TimeFormat.CLOCK_24H) 23 else 12) {
            add(hour)
        }
    }

    val minutes = mutableListOf<Int>().apply {
        for (minute in 0..59) {
            add(minute)
        }
    }
    val fontSize = maxOf(13, minOf(19, textSize))

    LaunchedEffect(selectedTime) {
        onTimeChanged(selectedTime.hour, selectedTime.minute, selectedTime.format)
    }

    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            .drawBehind {
                val strokeWidth = 1.dp.toPx()
                val halfStrokeWidth = strokeWidth / 2
                val startRect = size.height / 2 + fontSize.dp.toPx() / 2 + (halfStrokeWidth + 10)
                val endRect = size.height / 2 - fontSize.dp.toPx() / 2 - (halfStrokeWidth + 10)
                drawLine(
                    color = Color.Black,
                    start = Offset(0f, startRect),
                    end = Offset(size.width, startRect),
                    strokeWidth = strokeWidth
                )
                drawLine(
                    color = Color.Black,
                    start = Offset(0f, endRect),
                    end = Offset(size.width, endRect),
                    strokeWidth = strokeWidth
                )
            },
        contentAlignment = Alignment.Center
    ) {

        val height = (fontSize + 10).dp

        Row(
            modifier = modifier
                .fillMaxSize()
            ,
            verticalAlignment = Alignment.CenterVertically,
        ) {


            WheelView(modifier = Modifier.weight(3f),
                itemSize = DpSize(150.dp, height),
                selection = 0,
                itemCount = hours.size,
                rowOffset = offset,
                selectorOption = SelectorOptions().copy(
                    selectEffectEnabled = selectorEffectEnabled, enabled = false
                ),
                onFocusItem = {
                    selectedTime = selectedTime.copy(hour = hours[it])
                },
                content = {
                    Text(
                        text = if (hours[it] < 10) "0${hours[it]}" else "${hours[it]}",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.width(50.dp),
                        fontSize = fontSize.sp,
                        color = Color.Black
                    )
                })

            Text(
                text = "h",
                fontSize = fontSize.sp,
                color = Color.Black,
                modifier = Modifier

            )

            WheelView(modifier = Modifier.weight(3f),
                itemSize = DpSize(150.dp, height),
                selection = 0,
                itemCount = minutes.size,
                rowOffset = offset,
                selectorOption = SelectorOptions().copy(
                    selectEffectEnabled = selectorEffectEnabled, enabled = false
                ),
                onFocusItem = {
                    selectedTime = selectedTime.copy(minute = minutes[it])
                },
                content = {
                    Text(
                        text = if (minutes[it] < 10) "0${minutes[it]}" else "${minutes[it]}",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.width(100.dp),
                        fontSize = fontSize.sp,
                        color = Color.Black
                    )
                })

            Text(
                text = "m",
                fontSize = fontSize.sp,
                color = Color.Black,
                modifier = Modifier

            )


            if (timeFormat == TimeFormat.CLOCK_12H) {
                WheelView(modifier = Modifier.weight(4f),
                    itemSize = DpSize(150.dp, height),
                    selection = 0,
                    itemCount = formats.size,
                    rowOffset = offset,
                    isEndless = false,
                    selectorOption = SelectorOptions().copy(
                        selectEffectEnabled = selectorEffectEnabled, enabled = false
                    ),
                    onFocusItem = {
                        selectedTime = selectedTime.copy(format = formats[it])
                    },
                    content = {
                        Text(
                            text = formats[it].toString(),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.width(100.dp),
                            fontSize = fontSize.sp,
                            color = Color.Black
                        )
                    })
            }

        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = if (isSystemInDarkTheme()) darkPallet else lightPallet
                    )
                ),
        ) {}

        SelectorView(offset = offset)

    }
}

@Composable
fun SelectorView(modifier: Modifier = Modifier, offset: Int) {
    Column(
        modifier.fillMaxSize()
    ) {

        Box(
            modifier = Modifier
                .weight(offset.toFloat())
                .fillMaxWidth()
                .alpha(0.5f)
                .background(Color.White),
        )


        Column(
            modifier = Modifier
                .weight(1.13f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .height(0.5.dp)
                    .alpha(0.5f)
                    .background(Color.White)
                    .fillMaxWidth()
            )
            Box(
                modifier = Modifier
                    .height(0.5.dp)
                    .alpha(0.5f)
                    .background(Color.White)
                    .fillMaxWidth()
            )

        }



        Box(
            modifier = Modifier
                .weight(offset.toFloat())
                .fillMaxWidth()
                .alpha(0.5f)
                .background(Color.White),
        )
    }
}

val lightPallet = listOf(
    Color(0xE6FFFFFF),
    Color(0xE6FFFFFF),
    Color(0xB3FFFFFF),
    Color(0xB3FFFFFF),
    Color(0x80FFFFFF),
    Color(0x80FFFFFF),
    Color.Transparent,
    Color.Transparent,
    Color.Transparent,
    Color.Transparent,
    Color(0x80FFFFFF),
    Color(0x80FFFFFF),
    Color(0xB3FFFFFF),
    Color(0xB3FFFFFF),
    Color(0xE6FFFFFF),
    Color(0xE6FFFFFF),
)

val darkPallet = listOf(
    Color(0x80000000),
    Color(0x80000000),
    Color(0x80000000),
    Color(0x80000000),
    Color(0x80000000),
    Color(0x80000000),
    Color.Transparent,
    Color.Transparent,
    Color.Transparent,
    Color.Transparent,
    Color(0x80000000),
    Color(0x80000000),
    Color(0x80000000),
    Color(0x80000000),
    Color(0x80000000),
    Color(0x80000000),
)