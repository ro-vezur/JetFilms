package com.example.jetfilms.Repositories.Api

import com.example.jetfilms.API.ApiInterface
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ParticipantRepository @Inject constructor(private val apiService: ApiInterface) {
    suspend fun getParticipant(participantId: Int) = apiService.participant(participantId)

    suspend fun getParticipantFilmography(participantId: Int) = apiService.participantFilmography(participantId)

    suspend fun getParticipantImages(participantId: Int) = apiService.participantImages(participantId)
}