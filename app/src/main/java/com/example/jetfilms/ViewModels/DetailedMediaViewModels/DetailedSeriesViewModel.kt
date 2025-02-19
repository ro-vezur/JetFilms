package com.example.jetfilms.ViewModels.DetailedMediaViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetfilms.Models.DTOs.SeriesPackage.SeriesPageResponse
import com.example.jetfilms.Models.DTOs.TrailersResponse.TrailersResponse
import com.example.jetfilms.Models.DTOs.UnifiedDataPackage.MediaCreditsResponse
import com.example.jetfilms.Models.DTOs.UnifiedDataPackage.MediaImages.ImagesFromMediaResponse
import com.example.jetfilms.Models.Repositories.Api.ParticipantRepository
import com.example.jetfilms.Models.Repositories.Api.SeriesRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = DetailedSeriesViewModel.DetailedSeriesViewModelFactory::class)
class DetailedSeriesViewModel @AssistedInject constructor(
    @Assisted val seriesId: Int,
    private val seriesRepository: SeriesRepository,
    private val participantRepository: ParticipantRepository
): ViewModel() {

    @AssistedFactory
    interface DetailedSeriesViewModelFactory {
        fun create(seriesId: Int): DetailedSeriesViewModel
    }

    private val _seriesCast: MutableStateFlow<MediaCreditsResponse?> = MutableStateFlow(null)
    val seriesCast: StateFlow<MediaCreditsResponse?> = _seriesCast.asStateFlow()

    private val _seriesImages: MutableStateFlow<ImagesFromMediaResponse?> = MutableStateFlow(null)
    val seriesImages: StateFlow<ImagesFromMediaResponse?> = _seriesImages.asStateFlow()

    private val _similarSeries: MutableStateFlow<SeriesPageResponse?> = MutableStateFlow(null)
    val similarSeries: StateFlow<SeriesPageResponse?> = _similarSeries.asStateFlow()

    private val _seriesTrailers: MutableStateFlow<TrailersResponse?> = MutableStateFlow(null)
    val seriesTrailers: StateFlow<TrailersResponse?> = _seriesTrailers.asStateFlow()

    init {
        setSeriesCast()
        setSeriesImages()
        setSimilarSeries()
        setSeriesTrailers()
    }

    private fun setSeriesCast() = viewModelScope.launch {
        _seriesCast.emit(seriesRepository.getSeriesCredits(seriesId))
    }

    private fun setSeriesImages() = viewModelScope.launch {
        _seriesImages.emit(seriesRepository.getSeriesImages(seriesId))
    }

    private fun setSimilarSeries() = viewModelScope.launch {
        _similarSeries.emit(seriesRepository.getSimilarSerials(seriesId))
    }

    private fun setSeriesTrailers() = viewModelScope.launch {
        _seriesTrailers.emit(seriesRepository.getSeriesTrailers(seriesId))
    }

    suspend fun getSerialSeason(serialId: Int,seasonNumber: Int) = seriesRepository.getSerialSeason(serialId,seasonNumber)

    suspend fun getParticipant(participantId: Int) = participantRepository.getParticipant(participantId)
}