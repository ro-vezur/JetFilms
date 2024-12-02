package com.example.jetfilms.RoomLocalDataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.jetfilms.DTOs.SearchHistory_RoomDb.SearchedMedia

@Database(entities = [SearchedMedia::class], version = 1)
abstract class SearchedHistoryDataBase: RoomDatabase() {
    abstract val dao: DataBaseDao

    companion object{
        @Volatile
        private var Instance: SearchedHistoryDataBase? = null

        fun getSearchedHistoryDataBase(context: Context): SearchedHistoryDataBase {
            return Instance?: synchronized(this){
                Room.databaseBuilder(
                    context = context,
                    klass = SearchedHistoryDataBase::class.java,
                    name = "Search history"
                )
                    .build()
                    .also { Instance = it }
            }
        }
    }
}