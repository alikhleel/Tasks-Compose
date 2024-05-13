package com.example.taskscompose.screens.auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.taskscompose.R
import com.example.taskscompose.navigation.Screens


@Composable
fun SplashScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .then(modifier)
                .windowInsetsPadding(WindowInsets.safeDrawing),
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(.9f)
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.splash_logo),
                    contentDescription = "Logo",
                )
                Text(
                    text = "Tasks Todo",
                    fontWeight = FontWeight.Bold,
                    fontSize = 48.sp,
                    lineHeight = 32.sp,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Plan what you will do to be more organized for today, tomorrow and beyond",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 24.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                )
            }
            Column(
                modifier = Modifier.fillMaxWidth(.8f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                Button(modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.small,
                    onClick = {
                        navController.navigate(Screens.Auth.Login.route)
                    }) {
                    Text(
                        modifier = Modifier.padding(12.dp),
                        text = "Login", fontSize = 24.sp,

                        )
                }
                OutlinedButton(modifier = Modifier.fillMaxWidth(),
                    border = BorderStroke(0.dp, Color.Transparent),
                    onClick = { navController.navigate(Screens.Auth.SignUp.route) }) {
                    Text(
                        modifier = Modifier.padding(12.dp),
                        text = "Sign Up", fontSize = 24.sp,
                    )
                }
            }
        }
    }
}