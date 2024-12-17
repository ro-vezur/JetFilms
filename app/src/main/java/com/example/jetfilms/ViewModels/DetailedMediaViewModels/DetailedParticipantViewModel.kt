package com.example.jetfilms.ViewModels.DetailedMediaViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetfilms.Models.DTOs.ParticipantPackage.ParticipantFilmography
import com.example.jetfilms.Models.DTOs.ParticipantPackage.ParticipantImagesResponse
import com.example.jetfilms.Models.Repositories.Api.ParticipantRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel(assistedFactory = DetailedParticipantViewModel.DetailedParticipantViewModelFactory::class)
class DetailedParticipantViewModel @AssistedInject constructor(
    @Assisted val participantId: Int,
    private val participantRepository: ParticipantRepository
): ViewModel() {

    @AssistedFactory
    interface DetailedParticipantViewModelFactory {
        fun create(participantId: Int): DetailedParticipantViewModel
    }

    private val _selectedParticipantFilmography = MutableStateFlow(ParticipantFilmography())
    val selectedParticipantFilmography = _selectedParticipantFilmography.asStateFlow()

    private val _selectedParticipantImages = MutableStateFlow(ParticipantImagesResponse())
    val selectedParticipantImages = _selectedParticipantImages.asStateFlow()

    init {
        setParticipantFilmography(participantId)
        setParticipantImages(participantId)
    }

    fun setParticipantFilmography(participantId: Int) = viewModelScope.launch {
        _selectedParticipantFilmography.emit(participantRepository.getParticipantFilmography(participantId))
    }

    fun setParticipantImages(participantId: Int) = viewModelScope.launch {
        _selectedParticipantImages.emit(participantRepository.getParticipantImages(participantId))
    }
}