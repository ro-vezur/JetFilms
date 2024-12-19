package com.example.jetfilms.Hilt.Providers

import android.content.Context
import com.example.jetfilms.Models.Firebase.Auth.AuthService
import com.example.jetfilms.Models.Repositories.Firebase.AuthRepository
import com.example.jetfilms.Models.Repositories.Firebase.UsersCollectionRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseProvider {
    @Provides
    @Singleton
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideAuthRepository(
        @ApplicationContext context: Context,
        auth: FirebaseAuth
    ): AuthService {
        return AuthRepository(auth,context)
    }

    @Provides
    @Singleton
    fun provideFirebaseFireStore() = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideUsersCollectionRepository(fireStore: FirebaseFirestore): UsersCollectionRepository {
        return UsersCollectionRepository(fireStore)
    }
}