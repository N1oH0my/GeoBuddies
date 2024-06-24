package com.surf2024.geobuddies.data.common.module

import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.surf2024.geobuddies.domain.common.repository.ITokenRepository
import com.surf2024.geobuddies.data.common.repositoryimpl.TokenRepositoryImpl
import com.surf2024.geobuddies.data.common.repositoryimpl.UserInfoRepositoryImpl
import com.surf2024.geobuddies.domain.common.repository.IUserInfoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserInfoModule {
    @Provides
    @Singleton
    fun provideTokenRepositoryImpl(
        encryptedSharedPreferences : SharedPreferences
    ): ITokenRepository {
        Log.d("Hilt", "Creating TokenRepositoryImpl instance")
        return TokenRepositoryImpl(encryptedSharedPreferences)
    }

    @Provides
    @Singleton
    fun provideUserInfoRepositoryImpl(
        encryptedSharedPreferences : SharedPreferences,
        gson: Gson
    ): IUserInfoRepository {
        Log.d("Hilt", "Creating UserInfoRepositoryImpl instance")
        return UserInfoRepositoryImpl(encryptedSharedPreferences, gson)
    }
}