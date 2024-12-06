package com.example.jetfilms.Models.DTOs.SeriesPackage

import com.example.jetfilms.Models.DTOs.TrailersResponse.TrailersResponse
import com.example.jetfilms.Models.DTOs.UnifiedDataPackage.ImagesFromUnifiedMediaResponse
import com.example.jetfilms.Models.DTOs.UnifiedDataPackage.UnifiedMediaCreditsResponse

data class SerialDisplay(
    val response: DetailedSerialResponse,
    val serialCast: UnifiedMediaCreditsResponse,
    val serialImages: ImagesFromUnifiedMediaResponse,
    val similarSerials: SimplifiedSerialsResponse,
    val seriesTrailers: TrailersResponse
)
