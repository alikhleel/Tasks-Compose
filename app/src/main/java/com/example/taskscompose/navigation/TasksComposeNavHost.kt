package com.example.taskscompose.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.taskscompose.components.DatePickerDialog
import com.example.taskscompose.components.NewTagDialog
import com.example.taskscompose.components.TimePickerDialog
import com.example.taskscompose.screens.auth.AuthViewModel
import com.example.taskscompose.screens.auth.LoginScreen
import com.example.taskscompose.screens.auth.SignUpScreen
import com.example.taskscompose.screens.auth.SplashScreen
import com.example.taskscompose.screens.task.AddTaskScreen
import com.example.taskscompose.screens.task.AddTaskViewModel
import com.example.taskscompose.screens.task.CategoryScreen
import com.example.taskscompose.screens.task.CategoryViewModel
import com.example.taskscompose.screens.task.HomeScreen
import com.example.taskscompose.screens.task.TaskViewModel
import com.example.taskscompose.screens.task.TasksByCategory
import com.example.taskscompose.screens.task.TasksScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun TasksComposeNavHost(
    authViewModel: AuthViewModel, navController: NavHostController
) {

    val context = LocalContext.current
    NavHost(
        navController = navController,
        startDestination = authViewModel.isSignedIn.value,
    ) {
        authNavigation(navController, authViewModel)
        mainAppNavigation(navController) {
            authViewModel.logout(context)
        }
    }
}


fun NavGraphBuilder.authNavigation(
    navController: NavHostController, authViewModel: AuthViewModel
) {
    navigation(
        startDestination = Screens.Auth.Splash.route,
        route = Screens.Auth.route,
    ) {
        composable(Screens.Auth.Splash.route) {
            SplashScreen(navController)
        }
        composable(Screens.Auth.SignUp.route) {
            SignUpScreen(navController, authViewModel)
        }

        composable(Screens.Auth.Login.route) {
            LoginScreen(navController, authViewModel)
        }
    }
}


@SuppressLint("StateFlowValueCalledInComposition")
fun NavGraphBuilder.mainAppNavigation(
    navController: NavHostController, logout: () -> Unit
) {
    navigation(
        startDestination = Screens.MainApp.Home.route,
        route = Screens.MainApp.route,
    ) {
        composable(Screens.MainApp.Home.route) {
            val viewModel: TaskViewModel = hiltViewModel()
            val invoke = FirebaseAuth.getInstance().currentUser
            HomeScreen(
                invoke = invoke, navController = navController, viewModel = viewModel
            )
        }

        composable(Screens.MainApp.TaskByDate.route) {
            val viewModel: TaskViewModel = hiltViewModel()
            TasksScreen(viewModel = viewModel, navController = navController)
        }
        composable(Screens.MainApp.CategoryScreen.route) {
            val viewModel: CategoryViewModel = hiltViewModel()
            CategoryScreen(viewModel)
        }
        composable(Screens.MainApp.AddScreen.route) {
            val viewModel: AddTaskViewModel = hiltViewModel()

            AddTaskScreen(navController, viewModel)
        }

        composable(Screens.MainApp.EditScreen.route + "/{taskId}",
            arguments = listOf(navArgument("taskId") {
                type = NavType.LongType
            })) {
            val viewModel: AddTaskViewModel = hiltViewModel()
            val taskId = it.arguments?.getLong("taskId")

            AddTaskScreen(navController, viewModel, taskId)
        }

        dialog(Screens.MainApp.DateDialog.route) {
            DatePickerDialog(navController)
        }

        dialog(
            Screens.MainApp.TimeDialog.route + "/{argName}",
            arguments = listOf(navArgument("argName") {
                type = NavType.StringType
            })
        ) {
            val argName = it.arguments?.getString("argName")
            TimePickerDialog(navController, argName = argName ?: "")
        }

        dialog(Screens.MainApp.NewTagDialog.route) {
            val viewModel: AddTaskViewModel = hiltViewModel()

            NewTagDialog(onConfirm = { tag ->
                viewModel.addTags(tag)
                navController.popBackStack()
            }, onDismiss = { navController.popBackStack() })
        }

        composable(Screens.MainApp.StaticsScreen.route) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Green)
            ) {
                Text("Statics Screen")

            }
        }

        composable(Screens.MainApp.TaskByCategory.route + "/{type}",
            arguments = listOf(navArgument("type") {
                type = NavType.StringType
            })) { navArgument ->
            val taskViewModel: TaskViewModel = hiltViewModel()

            TasksByCategory(
                navController, taskViewModel, navArgument.arguments?.getString("type")
            )
        }
    }
}


fun NavOptionsBuilder.popUpToTop(navController: NavController) {
    popUpTo(navController.currentBackStackEntry?.destination?.route ?: return) {
        saveState = true
        inclusive = true
    }
}