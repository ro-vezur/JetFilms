package com.example.jetfilms.Models.Datastore

import android.content.Context
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.emptyPreferences
import androidx.datastore.preferences.preferencesKey
import com.example.jetfilms.PREFERENCES_NAME
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okio.IOException

class MyPreferences(context: Context) {
    private val datastore = context.createDataStore(PREFERENCES_NAME)

    val emailSentTimeFlow: Flow<Long> = datastore.data
        .catch {
            if(it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }.map { preferences ->
            val emailSentTime = preferences[SENT_EMAIL_TIME]?: 0L
            emailSentTime
        }

    suspend fun setEmailSentTime(timeMillis: Long) {
        datastore.edit { preferences ->
            preferences[SENT_EMAIL_TIME] = timeMillis
        }
    }

    companion object {
        val SENT_EMAIL_TIME = preferencesKey<Long>("sent email time")
    }
}