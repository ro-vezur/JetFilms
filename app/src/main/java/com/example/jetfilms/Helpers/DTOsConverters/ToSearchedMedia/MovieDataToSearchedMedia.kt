package com.example.jetfilms.Helpers.DTOsConverters.ToSearchedMedia

import com.example.jetfilms.Helpers.Date_formats.DateFormats
import com.example.jetfilms.Models.DTOs.MoviePackage.DetailedMovieResponse
import com.example.jetfilms.Models.DTOs.SearchHistory_RoomDb.SearchedMedia
import com.example.jetfilms.View.Screens.Start.Select_type.MediaCategories

fun MovieDataToSearchedMedia(movieData: DetailedMovieResponse): SearchedMedia {
    return SearchedMedia(
        id = "${movieData.id}${MediaCategories.MOVIE.format}",
        mediaId = movieData.id,
        mediaType = MediaCategories.MOVIE.format,
        viewedDateMillis = DateFormats.getCurrentDateMillis()
    )
}