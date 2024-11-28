package com.example.jetfilms.DTOs.SeriesPackage

import com.example.jetfilms.DTOs.UnifiedDataPackage.ImagesFromUnifiedMediaResponse
import com.example.jetfilms.DTOs.UnifiedDataPackage.UnifiedMediaCreditsResponse

data class SerialDisplay(
    val response: DetailedSerialResponse,
    val serialCast: UnifiedMediaCreditsResponse,
    val serialImages: ImagesFromUnifiedMediaResponse,
    val similarSerials: SimplifiedSerialsResponse
)
