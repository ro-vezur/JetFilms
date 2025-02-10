package com.example.jetfilms.ViewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetfilms.Models.DTOs.FavoriteMediaDTOs.FavoriteMedia
import com.example.jetfilms.Models.DTOs.UnifiedDataPackage.UnifiedMedia
import com.example.jetfilms.Models.DTOs.UserDTOs.User
import com.example.jetfilms.Models.Repositories.Api.MoviesRepository
import com.example.jetfilms.Models.Repositories.Api.SeriesRepository
import com.example.jetfilms.Models.Repositories.Firebase.UsersCollectionRepository
import com.example.jetfilms.View.Screens.Start.Select_type.MediaCategories
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteMediaViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val seriesRepository: SeriesRepository,
): ViewModel() {

    private val _favoriteMediaList: MutableStateFlow<List<UnifiedMedia>> = MutableStateFlow(listOf())
    val favoriteMediaList: StateFlow<List<UnifiedMedia>> = _favoriteMediaList.asStateFlow()

    fun setFavoriteMedia(newFavoriteMediaList: List<FavoriteMedia>) = viewModelScope.launch {

        _favoriteMediaList.emit(
            newFavoriteMediaList.sortedByDescending { it.addedDateMillis }.map { favoriteMedia ->
                if(favoriteMedia.mediaFormat == MediaCategories.MOVIE) {
                    convertFavoriteMovieToUnifiedMedia(favoriteMedia)
                } else {
                    convertFavoriteSeriesToUnifiedMedia(favoriteMedia)
                }
            }
        )

    }

    private suspend fun convertFavoriteMovieToUnifiedMedia(favoriteMovie: FavoriteMedia): UnifiedMedia {
        return UnifiedMedia.fromDetailedMovieResponse(moviesRepository.getMovie(favoriteMovie.mediaId))
    }

    private suspend fun convertFavoriteSeriesToUnifiedMedia(favoriteSeries: FavoriteMedia): UnifiedMedia {
        return UnifiedMedia.fromDetailedSeriesResponse(seriesRepository.getSerial(favoriteSeries.mediaId))
    }


}