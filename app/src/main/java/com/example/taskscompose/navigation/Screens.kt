package com.example.taskscompose.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screens(val route: String) {

    data object MainApp : Screens("mainGraph") {
        data object Home : Screens("home_screen")
        data object TaskByDate : Screens("task_by_date_screen")
        data object AddScreen : Screens("add_screen")
        data object CategoryScreen : Screens("category_screen")
        data object StaticsScreen : Screens("statics_screen")

        data object DateDialog : Screens("date_dialog")
        data object TimeDialog : Screens("time_dialog")
        data object NewTagDialog : Screens("new_tag_dialog")
        data object TaskByCategory : Screens("task_by_category")
        data object EditScreen : Screens("edit_screen")

        data object SettingsScreen : Screens("settings_screen")

    }

    data object Auth : Screens("authGraph") {
        data object Splash : Screens("splash")
        data object SignUp : Screens("signup_route")
        data object Login : Screens("login_route")
    }
}

data class BottomNavigationItem(
    val icon: ImageVector = Icons.Filled.Home,
    val route: String = "",
) {
    fun bottomNavigationItems(): List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(icon = Icons.Outlined.Home, route = "home_screen"),
            BottomNavigationItem(icon = Icons.Outlined.List, route = "task_by_date_screen"),
            BottomNavigationItem(icon = Icons.Outlined.AddCircle, route = "add_screen"),
            BottomNavigationItem(icon = Icons.Outlined.Settings, route = "category_screen"),
            BottomNavigationItem(icon = Icons.Outlined.DateRange, route = "statics_screen"),
        )
    }
}