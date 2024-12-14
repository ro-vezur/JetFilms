package com.example.jetfilms.Models.DTOs.SeriesPackage

import com.example.jetfilms.Models.DTOs.TrailersResponse.TrailersResponse
import com.example.jetfilms.Models.DTOs.UnifiedDataPackage.ImagesFromUnifiedMediaResponse
import com.example.jetfilms.Models.DTOs.UnifiedDataPackage.UnifiedMediaCreditsResponse

data class SeriesDisplay(
    val response: DetailedSerialResponse,
    val seriesCast: UnifiedMediaCreditsResponse,
    val seriesImages: ImagesFromUnifiedMediaResponse,
    val similarSeries: SeriesResponse,
    val seriesTrailers: TrailersResponse
)
