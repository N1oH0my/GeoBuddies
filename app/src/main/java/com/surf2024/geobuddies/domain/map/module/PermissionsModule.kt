package com.surf2024.geobuddies.domain.map.module

import android.content.Context
import com.surf2024.geobuddies.domain.map.utility.ILocationPermissionChecker
import com.surf2024.geobuddies.domain.map.utilityimpl.LocationPermissionCheckerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object PermissionsModule {
    @Provides
    fun provideLocationPermissionChecker(@ApplicationContext context: Context): ILocationPermissionChecker {
        return LocationPermissionCheckerImpl(context)
    }
}