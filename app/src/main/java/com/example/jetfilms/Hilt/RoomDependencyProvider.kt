package com.example.jetfilms.Hilt

import android.content.Context
import com.example.jetfilms.Models.Repositories.Room.SearchedHistoryRepository
import com.example.jetfilms.Models.RoomLocalDataBase.SearchedHistoryDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomDependencyProvider {
    @Provides
    @Singleton
    fun provideSearchHistoryRepository(@ApplicationContext context: Context): SearchedHistoryRepository {
        //   SearchedHistoryDataBase.getSearchedHistoryDataBase(context).close()
        //    context.deleteDatabase("Search history")
        return SearchedHistoryRepository(SearchedHistoryDataBase.getSearchedHistoryDataBase(context).dao)
    }
}