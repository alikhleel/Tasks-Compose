package com.example.taskscompose.ui

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.DialogNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.taskscompose.navigation.Screens
import com.example.taskscompose.navigation.TasksComposeNavHost
import com.example.taskscompose.screens.auth.AuthViewModel
import com.example.taskscompose.screens.auth.SplashScreen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class TestSplashScreen {
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()


    private lateinit var navController: NavHostController

    @Before
    fun init() {
        hiltRule.inject()
        composeTestRule.setContent {

            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            SplashScreen(navController = navController)
        }
    }
    @Test
    fun testSplashScreen() {
        composeTestRule.onNodeWithTag("Splash Screen").assertIsDisplayed()
    }

    @Test
    fun testSplashScreenContent() {
        composeTestRule.onNodeWithTag("Intro Image").assertIsDisplayed()
        composeTestRule.onNodeWithTag("App Name").assertIsDisplayed()
        composeTestRule.onNodeWithTag("App Description").assertIsDisplayed()
    }

    @Test
    fun testSplashScreenClickButton(){
        composeTestRule.onNodeWithTag("Login Button").assertIsDisplayed().assertHasClickAction()
        composeTestRule.onNodeWithTag("Sign Up Button").assertIsDisplayed().assertHasClickAction()

    }
}