package com.example.taskscompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.taskscompose.components.BottomBar
import com.example.taskscompose.navigation.Screens
import com.example.taskscompose.navigation.TasksComposeNavHost
import com.example.taskscompose.screens.auth.AuthViewModel
import com.example.taskscompose.ui.theme.TasksComposeTheme
import com.google.firebase.FirebaseApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContent {
            val authViewModel: AuthViewModel = hiltViewModel()
            TasksComposeTheme {

                val navController = rememberNavController()

                var showBottomBar by rememberSaveable { mutableStateOf(false) }
                val navBackStackEntry by navController.currentBackStackEntryAsState()

                showBottomBar = when (navBackStackEntry?.destination?.route) {
                    Screens.MainApp.Home.route -> true
                    Screens.MainApp.AddScreen.route -> true
                    Screens.MainApp.TaskByDate.route -> true
                    Screens.MainApp.CategoryScreen.route -> true
                    Screens.MainApp.StaticsScreen.route -> true
                    else -> false
                }
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .semantics {
                            contentDescription = "MyScreen"
                        },
                ) { paddingValues ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                    ) {

                        if (authViewModel.error.value.isNotEmpty()) {
                            Snackbar(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth(),
                                containerColor = Color.Red.copy(0.5f)
                            ) {
                                Text(
//                                modifier = Modifier.matchParentSize(),
                                    text = authViewModel.error.value
                                )
                            }
                        }
                        TasksComposeNavHost(authViewModel, navController)
                    }
                    if (showBottomBar) {
                        BottomBar(navController)
                    }
                }
            }
        }
    }
}