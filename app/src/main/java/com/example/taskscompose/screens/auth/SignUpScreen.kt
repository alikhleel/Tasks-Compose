package com.example.taskscompose.screens.auth


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.taskscompose.components.LoginWithGoogle
import com.example.taskscompose.ui.theme.PrimaryColor

@Composable
fun SignUpScreen(navController: NavHostController, authViewModel: AuthViewModel) {
    val emailState = remember { mutableStateOf(TextFieldValue()) }
    val passwordState = remember { mutableStateOf(TextFieldValue()) }
    val userName = remember { mutableStateOf(TextFieldValue()) }

    Text(
        modifier = Modifier.padding(vertical = 50.dp, horizontal = 16.dp),
        text = "Sign Up",
        color = PrimaryColor,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            leadingIcon = { Icon(Icons.Outlined.Person, contentDescription = "") },
            modifier = Modifier.fillMaxWidth(0.9f),
            value = userName.value,
            onValueChange = { userName.value = it },
            label = { Text("User Name") },
            colors = TextFieldDefaults.colors(
                disabledContainerColor = Color.White.copy(0.5f),
                focusedContainerColor = Color.White,
                disabledTextColor = Color.Gray.copy(0.5f),
                unfocusedContainerColor = Color.White.copy(0.2f),
                disabledIndicatorColor = Color.Gray.copy(0.2f),
                focusedIndicatorColor = Color.Gray.copy(0.2f),
                unfocusedIndicatorColor = Color.Gray.copy(0.2f),
                unfocusedTrailingIconColor = Color.Gray.copy(0.2f),
                focusedTextColor = PrimaryColor

            )
        )
        TextField(
            leadingIcon = { Icon(Icons.Outlined.Email, contentDescription = "") },
            modifier = Modifier.fillMaxWidth(0.9f),
            value = emailState.value,
            onValueChange = { emailState.value = it },
            label = { Text("Email") },
            colors = TextFieldDefaults.colors(
                disabledContainerColor = Color.White.copy(0.5f),
                focusedContainerColor = Color.White,
                disabledTextColor = Color.Gray.copy(0.5f),
                unfocusedContainerColor = Color.White.copy(0.2f),
                disabledIndicatorColor = Color.Gray.copy(0.2f),
                focusedIndicatorColor = Color.Gray.copy(0.2f),
                unfocusedIndicatorColor = Color.Gray.copy(0.2f),
                unfocusedTrailingIconColor = Color.Gray.copy(0.2f),
                focusedTextColor = PrimaryColor

            )
        )
        TextField(
            leadingIcon = { Icon(Icons.Outlined.Lock, contentDescription = "") },
            modifier = Modifier.fillMaxWidth(0.9f),
            value = passwordState.value,
            onValueChange = { passwordState.value = it },
            label = { Text("Password") },
            colors = TextFieldDefaults.colors(
                disabledContainerColor = Color.White.copy(0.5f),
                focusedContainerColor = Color.White,
                disabledTextColor = Color.Gray.copy(0.5f),
                unfocusedContainerColor = Color.White.copy(0.2f),
                disabledIndicatorColor = Color.Gray.copy(0.2f),
                focusedIndicatorColor = Color.Gray.copy(0.2f),
                unfocusedIndicatorColor = Color.Gray.copy(0.2f),
                unfocusedTrailingIconColor = Color.Gray.copy(0.2f),
                focusedTextColor = PrimaryColor

            ),
            visualTransformation = PasswordVisualTransformation()
        )



        Button(
            onClick = {
                if (emailState.value.text.isNotEmpty() && passwordState.value.text.isNotEmpty()) {
                    authViewModel.signup(
                        emailState.value.text.trim(), passwordState.value.text.trim()
                    )
                }
            },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(vertical = 50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryColor
            )
        ) {
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = "Create Account",
                fontSize = 16.sp,
                color = Color.White
            )

        }
        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Divider(color = Color.Gray.copy(0.2f))
            Text(
                modifier = Modifier
                    .background((Color.White))
                    .padding(vertical = 8.dp),
                text = "Or With",
                fontSize = 16.sp,
                color = Color.Gray.copy(0.8f)
            )
        }
        LoginWithGoogle()

    }
}