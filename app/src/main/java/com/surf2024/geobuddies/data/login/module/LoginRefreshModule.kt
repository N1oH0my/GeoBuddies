package com.surf2024.geobuddies.data.login.module

import android.content.SharedPreferences
import android.util.Log
import com.surf2024.geobuddies.data.login.repositoryimpl.AccessTokenSaverImpl
import com.surf2024.geobuddies.data.login.repositoryimpl.RefreshAccessTokenRepositoryImpl
import com.surf2024.geobuddies.domain.login.repository.IAccessTokenSaver
import com.surf2024.geobuddies.domain.login.repository.IRefreshAccessTokenRepository
import com.surf2024.geobuddies.domain.login.services.IRefreshAccessTokenService
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
    ): IRefreshAccessTokenService {
        Log.d("Hilt", "Creating IRefreshAccessTokenService retrofit client instance")
        return retrofit.create(IRefreshAccessTokenService::class.java)
    }

    @Provides
    @Singleton
    fun provideRefreshAccessTokenRepositoryImpl(
        refreshService: IRefreshAccessTokenService
    ): IRefreshAccessTokenRepository {
        Log.d("Hilt", "Creating RefreshAccessTokenRepositoryImpl instance")
        return RefreshAccessTokenRepositoryImpl(refreshService)
    }

}