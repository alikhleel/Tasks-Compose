package com.example.taskscompose.screens.auth

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.taskscompose.navigation.Screens
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModel() {
    var auth = Firebase.auth
    var isSignedIn =
        if (auth.currentUser == null) mutableStateOf(Screens.Auth.route) else mutableStateOf(
            Screens.MainApp.route
        )
    val error = mutableStateOf("")

    init {
        auth.apply {
            this.addAuthStateListener {
                isSignedIn.value =
                    if (currentUser == null) Screens.Auth.route else
                        Screens.MainApp.route

            }
        }
    }

    fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                error.value = task.exception?.message.orEmpty()
            }
        }
    }

    fun logout(context: Context) {
        auth.signOut()
        GoogleSignIn.getClient(
            context, GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
        ).signOut()
    }


    fun signup(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                error.value = task.exception?.message.orEmpty()
            }
        }
    }

    fun restPassword(email: String) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                error.value = task.exception?.message.orEmpty()
            }
        }
    }
}