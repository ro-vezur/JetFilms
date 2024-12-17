package com.example.jetfilms.ViewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetfilms.Helpers.DTOsConverters.ToUnifiedMedia.MovieDataToUnifiedMedia
import com.example.jetfilms.Helpers.DTOsConverters.ToUnifiedMedia.SeriesDataToUnifiedMedia
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

@HiltViewModel(assistedFactory = FavoriteMediaViewModel.FavoriteMediaViewModelFactory::class)
class FavoriteMediaViewModel @AssistedInject constructor(
    @Assisted val user: User,
    private val userCollectionRepository: UsersCollectionRepository,
    private val moviesRepository: MoviesRepository,
    private val seriesRepository: SeriesRepository,
): ViewModel() {

    @AssistedFactory
    interface FavoriteMediaViewModelFactory {
        fun create(user: User): FavoriteMediaViewModel
    }

    private val _favoriteMediaList: MutableStateFlow<List<UnifiedMedia>> = MutableStateFlow(listOf())
    val favoriteMediaList: StateFlow<List<UnifiedMedia>> = _favoriteMediaList.asStateFlow()

    init {
        viewModelScope.launch {
            Log.d("favorite media list",user.favoriteMediaList.toString())
         //   setFavoriteMedia(user.favoriteMediaList)
            Log.d("favorite media list to display",_favoriteMediaList.value.toString())
        }
    }

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
        return MovieDataToUnifiedMedia(moviesRepository.getMovie(favoriteMovie.mediaId))
    }

    private suspend fun convertFavoriteSeriesToUnifiedMedia(favoriteMovie: FavoriteMedia): UnifiedMedia {
        return SeriesDataToUnifiedMedia(seriesRepository.getSerial(favoriteMovie.mediaId))

    }


}