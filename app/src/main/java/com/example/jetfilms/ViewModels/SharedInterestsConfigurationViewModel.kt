package com.example.jetfilms.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetfilms.Models.Repositories.Firebase.UsersCollectionRepository
import com.example.jetfilms.View.Screens.Start.Select_genres.MediaGenres
import com.example.jetfilms.View.Screens.Start.Select_type.MediaCategories
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedInterestsConfigurationViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val usersCollectionRepository: UsersCollectionRepository
): ViewModel() {
    private val _mediaGenres: MutableStateFlow<List<MediaGenres>> = MutableStateFlow(listOf())
    val mediaGenres: StateFlow<List<MediaGenres>> = _mediaGenres.asStateFlow()

    private val _mediaCategories: MutableStateFlow<List<MediaCategories>> = MutableStateFlow(listOf())
    val mediaCategories: StateFlow<List<MediaCategories>> = _mediaCategories.asStateFlow()

    init {
        viewModelScope.launch {
            val firebaseUser = auth.currentUser
            val user = usersCollectionRepository.getUser(firebaseUser?.uid.toString())

            user?.let { checkedUser ->
                _mediaGenres.emit(checkedUser.recommendedMediaGenres)
                _mediaCategories.emit(checkedUser.recommendedMediaCategories)
            }
        }
    }

    fun setMediaGenres(newMediaGenres: List<MediaGenres>) = viewModelScope.launch {
        _mediaGenres.emit(newMediaGenres)
    }

    fun setMediaCategories(newMediaCategories: List<MediaCategories>) = viewModelScope.launch {
        _mediaCategories.emit(newMediaCategories)
    }

    fun setUser() = viewModelScope.launch {
        val firebaseUser = auth.currentUser
        val user = usersCollectionRepository.getUser(firebaseUser?.uid.toString())

        user?.let { checkedUser ->
            usersCollectionRepository.addOrUpdateUser(
                user = checkedUser.copy(
                    recommendedMediaGenres = mediaGenres.value.toMutableList(),
                    recommendedMediaCategories = mediaCategories.value.toMutableList(),
                ),
                onResult = {}
            )
        }
    }

}