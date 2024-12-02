package com.example.jetfilms.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetfilms.DTOs.ParticipantPackage.ParticipantFilmography
import com.example.jetfilms.DTOs.ParticipantPackage.ParticipantImagesResponse
import com.example.jetfilms.Repositories.Api.ParticipantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class ParticipantViewModel @Inject constructor(
    private val participantRepository: ParticipantRepository
): ViewModel() {

    private val _selectedParticipantFilmography = MutableStateFlow<ParticipantFilmography?>(null)
    val selectedParticipantFilmography = _selectedParticipantFilmography.asStateFlow()

    private val _selectedParticipantImages = MutableStateFlow<ParticipantImagesResponse?>(null)
    val selectedParticipantImages = _selectedParticipantImages.asStateFlow()

    fun setParticipantFilmography(participantId: Int){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _selectedParticipantFilmography.emit(participantRepository.getParticipantFilmography(participantId))
            }
        }
    }

    fun setParticipantImages(participantId: Int){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _selectedParticipantImages.emit(participantRepository.getParticipantImages(participantId))
            }
        }
    }

    suspend fun getParticipant(participantId: Int) = participantRepository.getParticipant(participantId)
}