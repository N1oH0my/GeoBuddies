package com.surf2024.geobuddies.domain.map.module

import android.content.Context
import com.surf2024.geobuddies.domain.map.repository.ILocationRepository
import com.surf2024.geobuddies.domain.map.repositoryimpl.LocationRepositoryImpl
import com.surf2024.geobuddies.domain.map.utility.ILocationPermissionChecker
import com.surf2024.geobuddies.domain.map.utilityimpl.LocationPermissionChecker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object LocationModule {
    @Provides
    fun provideLocationPermissionChecker(@ApplicationContext context: Context): ILocationPermissionChecker {
        return LocationPermissionChecker(context)
    }
    @Provides
    fun provideLocationRepository(
        @ApplicationContext context: Context,
        locationPermissionChecker: ILocationPermissionChecker
    ): ILocationRepository {
        return LocationRepositoryImpl(context, locationPermissionChecker)
    }
}