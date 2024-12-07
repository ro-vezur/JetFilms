package com.example.jetfilms.Models.Firebase

import com.example.jetfilms.Models.DTOs.UserDTOs.User

interface UsersCollectionService {
    fun addOrUpdateUser(user: User, onResult: (Throwable?) -> Unit)
    suspend fun getUser(userId: String): User?
    fun deleteUser(userId: String)
}