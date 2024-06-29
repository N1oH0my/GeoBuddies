package com.surf2024.geobuddies.data.login.module

import android.content.SharedPreferences
import android.util.Log
import com.surf2024.geobuddies.data.login.repositoryimpl.AccessTokenSaverImpl
import com.surf2024.geobuddies.data.login.repositoryimpl.RefreshTokenRepositoryImpl
import com.surf2024.geobuddies.domain.login.repository.IAccessTokenSaver
import com.surf2024.geobuddies.domain.login.repository.IRefreshTokenRepository
import com.surf2024.geobuddies.domain.login.services.IRefreshTokenService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoginRefreshModule {

    @Provides
    @Singleton
    fun provideRefreshTokenService(
        @Named("withRefreshJwt") retrofit: Retrofit
    ): IRefreshTokenService {
        Log.d("Hilt", "Creating IRefreshTokenService retrofit client instance")
        return retrofit.create(IRefreshTokenService::class.java)
    }

    @Provides
    @Singleton
    fun provideRefreshTokenRepositoryImpl(
        refreshService: IRefreshTokenService
    ): IRefreshTokenRepository {
        Log.d("Hilt", "Creating RefreshTokenRepositoryImpl instance")
        return RefreshTokenRepositoryImpl(refreshService)
    }

    @Provides
    @Singleton
    fun provideAccessTokenSaverImpl(
        encryptedSharedPreference: SharedPreferences
    ): IAccessTokenSaver {
        Log.d("Hilt", "Creating AccessTokenSaverImpl instance")
        return AccessTokenSaverImpl(encryptedSharedPreference)
    }

}