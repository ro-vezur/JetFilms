package com.example.jetfilms.Models.DTOs.SeriesPackage

import com.example.jetfilms.Models.DTOs.TrailersResponse.TrailersResponse
import com.example.jetfilms.Models.DTOs.UnifiedDataPackage.MediaCreditsResponse
import com.example.jetfilms.Models.DTOs.UnifiedDataPackage.MediaImages.ImagesFromMediaResponse

data class SeriesDisplay(
    val response: DetailedSeriesResponse,
    val seriesCast: MediaCreditsResponse,
    val seriesImages: ImagesFromMediaResponse,
    val similarSeries: SeriesPageResponse,
    val seriesTrailers: TrailersResponse
)
