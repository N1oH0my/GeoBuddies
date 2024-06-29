package com.surf2024.geobuddies.data.login.module

import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.surf2024.geobuddies.data.login.repositoryimpl.AccessTokenSaverImpl
import com.surf2024.geobuddies.domain.login.repository.ILoginResponseSaver
import com.surf2024.geobuddies.data.login.repositoryimpl.LoginResponseSaverImpl
import com.surf2024.geobuddies.domain.login.repository.IAccessTokenSaver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoginResponseSaverModule {

    @Provides
    @Singleton
    fun provideLoginResponseSaverRepositoryImpl(
        encryptedSharedPreference: SharedPreferences,
        gson: Gson
    ): ILoginResponseSaver{
        Log.d("Hilt", "Creating LoginResponseSaverRepositoryImpl instance")
        return LoginResponseSaverImpl(encryptedSharedPreference, gson)
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