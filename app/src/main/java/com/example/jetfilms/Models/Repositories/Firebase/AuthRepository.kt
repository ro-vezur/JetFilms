package com.example.jetfilms.Models.Repositories.Firebase

import com.example.jetfilms.Models.Firebase.AuthService
import com.example.jetfilms.Models.Firebase.Resource
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth
): AuthService {
    override fun loginUser(email: String, password: String): Flow<Resource<AuthResult>> {
        return flow {
            emit(Resource.Loading())
            val result = firebaseAuth.signInWithEmailAndPassword(email,password).await()
            emit(Resource.Success(data = result))
        }.catch {
            emit(Resource.Error(message = it.message.toString()))
        }
    }

    override fun signUp(name: String,email: String, password: String): Flow<Resource<AuthResult>> {
       return flow {
           emit(Resource.Loading())

           val result = firebaseAuth.createUserWithEmailAndPassword(email,password).await()
           val profile = UserProfileChangeRequest.Builder().setDisplayName(name).build()
           result.user?.run {
             updateProfile(profile)
           }

           emit(Resource.Success(data = result))
       }.catch {
           emit(Resource.Error(message = it.message.toString()))
       }
    }
}