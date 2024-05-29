package com.example.taskscompose.utils


import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

object DateUtils {

    fun stringToLocalDate(date: String): LocalDate {
        val dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        val localDate = LocalDate.parse(date, dtf)
        return localDate
    }

    fun localDateToString(date: LocalDate): String {
        val dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        val formattedDate = date.format(dtf)
        return formattedDate
    }


    fun getTimeMiles(year: Int, month: Int, day: Int): Long {
        val calendar = Calendar.getInstance()
        calendar[Calendar.YEAR] = year
        calendar[Calendar.MONTH] = month
        val maxDayCount = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        calendar[Calendar.DAY_OF_MONTH] = Math.min(day, maxDayCount)
        return calendar.timeInMillis
    }

    fun getCurrentTime(): Long {
        val calendar = Calendar.getInstance()
        return calendar.timeInMillis
    }

    fun getMonthDayCount(timeStamp: Long): Int {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeStamp
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    }

    fun getDay(timeStamp: Long): Int {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeStamp
        return calendar[Calendar.DAY_OF_MONTH]
    }

    fun getMonth(timeStamp: Long): Int {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeStamp
        return calendar[Calendar.MONTH]
    }

    fun getYear(timeStamp: Long): Int {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeStamp
        return calendar[Calendar.YEAR]
    }


    fun getCurrentHour(): Int {
        val calendar = Calendar.getInstance()
        return calendar[Calendar.HOUR_OF_DAY]
    }

    fun getCurrentDate(): LocalDate {
        return LocalDate.now()
    }


    fun getCurrentMinute(): Int {
        val calendar = Calendar.getInstance()
        return calendar[Calendar.MINUTE]
    }

    fun convertAndFormatTime(hour: Int, minute: Int, period: String): String {
        val localTime = if (period.equals("AM", ignoreCase = true)) {
            LocalTime.of(hour % 12, minute)
        } else {
            LocalTime.of((hour % 12) + 12, minute)
        }
        val outputFormatter = DateTimeFormatter.ofPattern("HH:mm")
        return localTime.format(outputFormatter)
    }

    fun formatTimeStamp(timeStamp: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeStamp
        val hour = calendar[Calendar.HOUR_OF_DAY]
        val minute = calendar[Calendar.MINUTE]
        val localTime = LocalTime.of(hour, minute)
        val outputFormatter = DateTimeFormatter.ofPattern("HH:mm")
        return localTime.format(outputFormatter)
    }
}