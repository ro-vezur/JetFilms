package com.example.jetfilms.ViewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetfilms.Models.DTOs.FavoriteMediaDTOs.FavoriteMedia
import com.example.jetfilms.Models.DTOs.UnifiedDataPackage.UnifiedMedia
import com.example.jetfilms.Models.DTOs.UserDTOs.User
import com.example.jetfilms.Models.Firebase.AuthService
import com.example.jetfilms.Models.Firebase.Resource
import com.example.jetfilms.Models.Repositories.Firebase.UsersCollectionRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val usersCollectionRepository: UsersCollectionRepository,
    private val authRepository: AuthService
): ViewModel() {
    private val _firebaseUser: MutableStateFlow<FirebaseUser?> = MutableStateFlow(auth.currentUser)
    val firebaseUser: StateFlow<FirebaseUser?> = _firebaseUser.asStateFlow()

    private val _user: MutableStateFlow<User?> = MutableStateFlow(null)
    val user = _user.asStateFlow()

    init {
        viewModelScope.launch{
            _user.emit(getUser(_firebaseUser.value?.uid.toString()))
        }
    }

    fun setUser(newUser: User?) = viewModelScope.launch {
        _user.emit(newUser)
    }

    fun addOrUpdateUser(newUser: User) {
        usersCollectionRepository.addOrUpdateUser(newUser,{})
    }

    suspend fun getUser(userId: String): User? {
        return usersCollectionRepository.getUser(userId)
    }

    fun addFavoriteMedia(media: FavoriteMedia) = viewModelScope.launch {
        _user.value?.let { checkedUser ->
            val favoriteMediaList = checkedUser.favoriteMediaList
            Log.d("checked user",checkedUser.toString())
            Log.d("favorite media list",favoriteMediaList.toString())
            Log.d("media in list",(favoriteMediaList.find { it.id == media.id }).toString())
            if(favoriteMediaList.find { it.id == media.id } != null) {
                favoriteMediaList.removeAll { it.id == media.id }
                setUser(checkedUser.copy(favoriteMediaList = favoriteMediaList))
                usersCollectionRepository.addFavoriteMedia(checkedUser.id,favoriteMediaList)
            } else {
                favoriteMediaList.add(media)
                setUser(checkedUser.copy(favoriteMediaList = favoriteMediaList))
                usersCollectionRepository.addFavoriteMedia(checkedUser.id,favoriteMediaList)
            }

        }

    }

    fun signUp(user: User, onSuccess: (userId: String) -> Unit) = viewModelScope.launch{
        user.run{
            authRepository.signUp("$firstName $lastName", email, password).collectLatest { result ->
                when(result){
                    is Resource.Success -> {
                        _firebaseUser.emit(auth.currentUser)
                        onSuccess(result.data?.user?.uid.toString())
                    }
                    is Resource.Loading -> {}
                    is Resource.Error -> {}
                }
            }
        }
    }

    fun logIn(email: String, password: String) = viewModelScope.launch {
        authRepository.loginUser(email, password).collectLatest { result ->
            when(result){
                is Resource.Success -> {
                    _firebaseUser.emit(auth.currentUser)
                    setUser(getUser(auth.currentUser?.uid.toString()))
                }
                is Resource.Loading -> {}
                is Resource.Error -> {}
            }
        }
    }

    fun logOut() = viewModelScope.launch {
        auth.signOut()
        _firebaseUser.emit(null)
    }
}