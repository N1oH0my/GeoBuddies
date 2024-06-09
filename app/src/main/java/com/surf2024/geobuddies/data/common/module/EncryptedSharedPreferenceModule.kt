package com.surf2024.geobuddies.data.common.module

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EncryptedSharedPreferenceModule {
    @Provides
    @Singleton
    fun provideEncryptedSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        Log.d("SharedPreferences", "Creating EncryptedSharedPreference instance")
        return EncryptedSharedPreferences.create(
            context,
            ENCRYPTED_SHARED_PREFERENCE_NAME,
            MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    private const val ENCRYPTED_SHARED_PREFERENCE_NAME = "secure_prefs"

}