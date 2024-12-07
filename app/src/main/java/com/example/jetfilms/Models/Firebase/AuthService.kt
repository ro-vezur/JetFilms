package com.example.jetfilms.Models.Firebase

import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface AuthService {
    fun loginUser(email: String, password: String): Flow<Resource<AuthResult>>

    fun signUp(name: String,email: String,password: String): Flow<Resource<AuthResult>>
}