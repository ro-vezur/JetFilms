package com.example.jetfilms

import com.example.jetfilms.API.ApiInterface
import com.example.jetfilms.Repositories.MoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object AppModule {

    @Provides
    @ViewModelScoped
    fun provideRetrofit() : Retrofit = Retrofit.Builder()
        .baseUrl(baseAPIUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @ViewModelScoped
    fun provideMoviesService(retrofit : Retrofit) : ApiInterface {
       return retrofit.create(ApiInterface::class.java)
    }

    @Provides
    @ViewModelScoped
    fun provideMoviesRepository(apiInterface: ApiInterface): MoviesRepository{
        return MoviesRepository(apiInterface)
    }
}