package com.example.taskscompose.ui.theme

import com.example.taskscompose.R

enum class Languages(
    val language: Int, val code: String,
) {
    English(R.string.en, "en"),
    Arabic(R.string.ar, "ar")
}

fun convertLanguage(code: String): Languages {
    return when (code) {
        "en" -> Languages.English
        "ar" -> Languages.Arabic
        else -> Languages.English
    }
}