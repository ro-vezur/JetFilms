package com.example.jetfilms.Models.Firebase.Firestore

import com.example.jetfilms.Models.DTOs.FavoriteMediaDTOs.FavoriteMedia
import com.example.jetfilms.Models.DTOs.UserDTOs.User

interface UsersCollectionService {
    fun addOrUpdateUser(user: User, onResult: (Throwable?) -> Unit)
    suspend fun getUser(userId: String): User?

    suspend fun isCustomProviderUsed(email: String): Boolean
    suspend fun checkIfEmailIsRegistered(emailToCheck: String): Boolean
    suspend fun checkIfPasswordMatches(email: String,password: String): Boolean
    suspend fun addFavoriteMedia(userId: String,mediaList: MutableList<FavoriteMedia>)
    fun deleteUser(userId: String)
}