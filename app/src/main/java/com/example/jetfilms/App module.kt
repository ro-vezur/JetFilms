package com.example.jetfilms

import android.content.Context
import com.example.jetfilms.API.ApiInterceptor
import com.example.jetfilms.API.ApiInterface
import com.example.jetfilms.Repositories.Api.MoviesRepository
import com.example.jetfilms.Repositories.Api.ParticipantRepository
import com.example.jetfilms.Repositories.Api.SeriesRepository
import com.example.jetfilms.Repositories.Api.UnifiedMediaRepository
import com.example.jetfilms.Repositories.Room.SearchedHistoryRepository
import com.example.jetfilms.RoomLocalDataBase.SearchedHistoryDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ViewModelComponent::class)
object AppModule {

    @Provides
    @ViewModelScoped
    fun provideSearchHistoryRepository(@ApplicationContext context: Context): SearchedHistoryRepository {

        return SearchedHistoryRepository(SearchedHistoryDataBase.getSearchedHistoryDataBase(context).dao)
    }

    @Provides
    @ViewModelScoped
    fun provideOkhttp(): OkHttpClient = OkHttpClient()
        .newBuilder()
        .addInterceptor(ApiInterceptor(API_KEY))
        .build()

    @Provides
    @ViewModelScoped
    fun provideRetrofit(okHttpClient: OkHttpClient) : Retrofit = Retrofit.Builder()
        .baseUrl(BASE_API_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @ViewModelScoped
    fun provideMoviesService(retrofit : Retrofit) : ApiInterface {
       return retrofit.create(ApiInterface::class.java)
    }

    @Provides
    @ViewModelScoped
    fun provideMoviesRepository(apiInterface: ApiInterface): MoviesRepository {
        return MoviesRepository(apiInterface)
    }

    @Provides
    @ViewModelScoped
    fun provideSeriesRepository(apiInterface: ApiInterface): SeriesRepository {
        return SeriesRepository(apiInterface)
    }

    @Provides
    @ViewModelScoped
    fun provideParticipantRepository(apiInterface: ApiInterface): ParticipantRepository {
        return ParticipantRepository(apiInterface)
    }

    @Provides
    @ViewModelScoped
    fun provideUnifiedMediaRepository(apiInterface: ApiInterface): UnifiedMediaRepository {
        return UnifiedMediaRepository(apiInterface)
    }
}