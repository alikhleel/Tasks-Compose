package com.example.taskscompose.ui

import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.DialogNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.taskscompose.MainActivity
import com.example.taskscompose.components.BottomBar
import com.example.taskscompose.navigation.Screens
import com.example.taskscompose.navigation.TasksComposeNavHost
import com.example.taskscompose.screens.auth.AuthViewModel
import com.example.taskscompose.ui.theme.TasksComposeTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@HiltAndroidTest
class TestTabNavigation {
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var authViewModel: AuthViewModel
    private lateinit var navController: NavHostController

    @Before
    fun init() {
        hiltRule.inject()
        composeTestRule.activity.setContent {
            authViewModel = hiltViewModel()
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(DialogNavigator())
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            TasksComposeTheme {
                TasksComposeNavHost(authViewModel = authViewModel, navController = navController)
                BottomBar(navController = navController)
            }
        }


    }

    @Test
    fun testBottomNavigationBar() {
        composeTestRule.onNodeWithTag("Navigate To Add Screen").assertIsDisplayed().performClick()
        navController.assertCurrentRouteName(Screens.MainApp.AddScreen.route)

        composeTestRule.onNodeWithTag(Screens.MainApp.TaskByDate.route).assertIsDisplayed()
            .performClick()
        navController.assertCurrentRouteName(Screens.MainApp.TaskByDate.route)

        composeTestRule.onNodeWithTag(Screens.MainApp.Home.route).assertIsDisplayed().performClick()
        navController.assertCurrentRouteName(Screens.MainApp.Home.route)

        composeTestRule.onNodeWithTag(Screens.MainApp.CategoryScreen.route).assertIsDisplayed()
            .performClick()
        navController.assertCurrentRouteName(Screens.MainApp.CategoryScreen.route)

        composeTestRule.onNodeWithTag(Screens.MainApp.StaticsScreen.route).assertIsDisplayed()
            .performClick()
        navController.assertCurrentRouteName(Screens.MainApp.StaticsScreen.route)
    }


}

fun NavController.assertCurrentRouteName(expectedRouteName: String) {
    assert(this.currentBackStackEntry?.destination?.route == expectedRouteName)
}