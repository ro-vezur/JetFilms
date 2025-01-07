package com.example.jetfilms.ViewModels.UserViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetfilms.Models.DTOs.FavoriteMediaDTOs.FavoriteMedia
import com.example.jetfilms.Models.DTOs.UserDTOs.User
import com.example.jetfilms.Models.Firebase.Auth.AuthService
import com.example.jetfilms.Models.Firebase.Auth.Resource
import com.example.jetfilms.Models.Repositories.Firebase.UsersCollectionRepository
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

private val TAG = "Google "

@HiltViewModel
class UserViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val usersCollectionRepository: UsersCollectionRepository,
    private val authRepository: AuthService,
): ViewModel() {

    private val _firebaseUser: MutableStateFlow<FirebaseUser?> = MutableStateFlow(auth.currentUser)
    val firebaseUser: StateFlow<FirebaseUser?> = _firebaseUser.asStateFlow()

    private val _user: MutableStateFlow<User?> = MutableStateFlow(null)
    val user = _user.asStateFlow()

    init {
        viewModelScope.launch{
            setUser(getUser(_firebaseUser.value?.uid.toString()))
        }
    }

    fun setUser(newUser: User?) {
        viewModelScope.launch {
            _user.emit(newUser)
        }
    }

    fun addOrUpdateUser(newUser: User) {
        usersCollectionRepository.addOrUpdateUser(newUser,{})
        setUser(newUser)
    }

    suspend fun getUser(userId: String): User? {
        return usersCollectionRepository.getUser(userId)
    }

    fun addFavoriteMedia(media: FavoriteMedia) = viewModelScope.launch {
        _user.value?.let { checkedUser ->
            val favoriteMediaList = checkedUser.favoriteMediaList

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
                    _firebaseUser.emit(result.data?.user)
                    setUser(getUser(result.data?.user?.uid.toString()))
                }
                is Resource.Loading -> {}
                is Resource.Error -> {}
            }
        }
    }

    fun logInWithGoogle() = viewModelScope.launch {
        authRepository.logInWithGoogle().collectLatest { result ->
            when(result){
                is Resource.Success -> {
                    val userFromResult = result.data?.user

                    _firebaseUser.emit(userFromResult)

                    userFromResult?.run {
                        val findUser = usersCollectionRepository.getUser(uid)

                        if(findUser == null) {
                            val newUser = User.noCustomSignInUser(userFromResult)
                            usersCollectionRepository.addOrUpdateUser(newUser,{})
                        } else {
                            setUser(usersCollectionRepository.getUser(uid))
                        }
                    }

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

    fun updatePassword(email: String,oldPassword: String,newPassword: String) {
        val credentials = EmailAuthProvider.getCredential(email,oldPassword)
        _firebaseUser.value?.reauthenticate(credentials)?.addOnCompleteListener { task ->
            if(task.isSuccessful) {
                auth.currentUser!!.updatePassword(newPassword)
            }
        }
    }
}