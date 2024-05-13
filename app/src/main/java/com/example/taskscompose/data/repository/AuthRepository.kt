package com.example.taskscompose.data.repository

import com.example.taskscompose.data.model.UIState
import com.example.taskscompose.utils.await
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject
import javax.inject.Singleton


@Singleton

class AuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {
    val currentUser = firebaseAuth.currentUser


    suspend fun login(email: String, password: String) {
        try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            UIState.Success(result.user)
        } catch (e: Exception) {
            e.printStackTrace()
            UIState.Error(e.message ?: "An error occurred while logging in")
        }

    }

    suspend fun signup(name: String, email: String, password: String) {
        try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()

            UIState.Success(result.user)
        } catch (e: Exception) {
            e.printStackTrace()
            UIState.Error(e.message ?: "An error occurred while logging in")
        }

    }
}