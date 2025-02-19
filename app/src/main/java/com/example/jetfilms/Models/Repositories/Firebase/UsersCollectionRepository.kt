package com.example.jetfilms.Models.Repositories.Firebase

import android.util.Log
import com.example.jetfilms.Models.DTOs.FavoriteMediaDTOs.FavoriteMedia
import com.example.jetfilms.Models.DTOs.UserDTOs.User
import com.example.jetfilms.Models.Firebase.Firestore.UsersCollectionService
import com.example.jetfilms.USERS_COLLECTION
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UsersCollectionRepository @Inject constructor(
    private val fireStore: FirebaseFirestore
): UsersCollectionService {
    override fun addOrUpdateUser(user: User, onResult: (Throwable?) -> Unit) {
        fireStore
            .collection(USERS_COLLECTION)
            .document(user.id)
            .set(user)
            .addOnCompleteListener{ onResult(it.exception) }
    }

    override suspend fun getUser(userId: String): User? {
        val document = fireStore.collection(USERS_COLLECTION).document(userId).get().await()
        return document.toObject(User::class.java)
    }

    override suspend fun isCustomProviderUsed(email: String): Boolean {
        val users = fireStore.collection(USERS_COLLECTION).get().await()

        return users.documents.find {
            val user = it.toObject(User::class.java)
            user?.email == email && user.customProviderUsed
        } != null
    }

    override suspend fun checkIfEmailIsRegistered(emailToCheck: String): Boolean {
        val users = fireStore.collection(USERS_COLLECTION).get().await()
        return users.documents.find {
            val user = it.toObject(User::class.java)
            user?.email == emailToCheck
        } != null
    }

    override suspend fun checkIfPasswordMatches(email: String, password: String): Boolean {
        val users = fireStore.collection(USERS_COLLECTION).get().await()
        return users.documents.find {
            val user = it.toObject(User::class.java)

            user?.email == email && user.password == password
        } != null
    }

    override suspend fun addFavoriteMedia(userId: String, mediaList: MutableList<FavoriteMedia>) {
        fireStore.runTransaction {
            fireStore.collection(USERS_COLLECTION).document(userId).update("favoriteMediaList",mediaList)
        }

    }

    override fun deleteUser(userId: String) {

    }

}