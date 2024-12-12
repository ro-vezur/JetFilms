package com.example.jetfilms.Hilt

import com.example.jetfilms.API_KEY
import com.example.jetfilms.BASE_API_URL
import com.example.jetfilms.Models.API.ApiInterceptor
import com.example.jetfilms.Models.API.ApiInterface
import com.example.jetfilms.Models.Repositories.Api.MoviesRepository
import com.example.jetfilms.Models.Repositories.Api.ParticipantRepository
import com.example.jetfilms.Models.Repositories.Api.SearchRepository
import com.example.jetfilms.Models.Repositories.Api.SeriesRepository
import com.example.jetfilms.Models.Repositories.Api.FilterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProvideAPI {
    @Provides
    @Singleton
    fun provideOkhttp(): OkHttpClient = OkHttpClient()
        .newBuilder()
        .addInterceptor(ApiInterceptor(API_KEY))
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient) : Retrofit = Retrofit.Builder()
        .baseUrl(BASE_API_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideMoviesService(retrofit : Retrofit) : ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }

    @Provides
    @Singleton
    fun provideMoviesRepository(apiInterface: ApiInterface): MoviesRepository {
        return MoviesRepository(apiInterface)
    }

    @Provides
    @Singleton
    fun provideSeriesRepository(apiInterface: ApiInterface): SeriesRepository {
        return SeriesRepository(apiInterface)
    }

    @Provides
    @Singleton
    fun provideParticipantRepository(apiInterface: ApiInterface): ParticipantRepository {
        return ParticipantRepository(apiInterface)
    }

    @Provides
    @Singleton
    fun provideUnifiedMediaRepository(apiInterface: ApiInterface): FilterRepository {
        return FilterRepository(apiInterface)
    }

    @Provides
    @Singleton
    fun provideSearchRepository(apiInterface: ApiInterface): SearchRepository {
        return SearchRepository(apiInterface)
    }
}