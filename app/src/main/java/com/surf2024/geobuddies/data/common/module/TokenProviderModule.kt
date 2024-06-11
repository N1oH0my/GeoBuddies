package com.surf2024.geobuddies.data.common.module

import android.content.SharedPreferences
import android.util.Log
import com.surf2024.geobuddies.domain.common.repository.ITokenProvider
import com.surf2024.geobuddies.domain.common.repositoryimpl.TokenProviderimpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TokenProviderModule {
    @Provides
    @Singleton
    fun provideTokenProvider(
        encryptedSharedPreferences : SharedPreferences
    ): ITokenProvider{
        Log.d("Hilt", "Creating TokenProvider instance")
        return TokenProviderimpl(encryptedSharedPreferences)
    }
}