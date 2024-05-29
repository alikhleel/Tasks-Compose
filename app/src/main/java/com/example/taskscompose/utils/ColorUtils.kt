package com.example.taskscompose.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

object ColorUtils {
    fun stringToColor(
        color: String, defaultColor: Color = Color.White
    ): Color {
        return Color(color.toIntOrNull() ?: defaultColor.toArgb())
    }

    fun colorToString(color: Color): String {
        return color.toArgb().toString()
    }

}