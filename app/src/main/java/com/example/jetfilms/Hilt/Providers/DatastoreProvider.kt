package com.example.jetfilms.Hilt.Providers

import android.content.Context
import com.example.jetfilms.Models.Datastore.MyPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatastoreProvider {

    @Provides
    @Singleton
    fun provideMyPreferences(
        @ApplicationContext context: Context
    ) = MyPreferences(context)
}