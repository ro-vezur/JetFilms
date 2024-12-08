package com.example.jetfilms.Models.Firebase

import com.example.jetfilms.Models.DTOs.UserDTOs.User

interface UsersCollectionService {
    fun addOrUpdateUser(user: User, onResult: (Throwable?) -> Unit)
    suspend fun getUser(userId: String): User?
    suspend fun checkIfEmailIsRegistered(emailToCheck: String): Boolean
    suspend fun checkIfPasswordMatches(email: String,password: String): Boolean
    fun deleteUser(userId: String)
}