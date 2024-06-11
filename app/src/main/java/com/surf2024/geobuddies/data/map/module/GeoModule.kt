package com.surf2024.geobuddies.data.map.module

import android.util.Log
import com.surf2024.geobuddies.data.map.repositoryimpl.GetFriendsGeoRepositoryImpl
import com.surf2024.geobuddies.data.map.repositoryimpl.SaveUserGeoRepositoryImpl
import com.surf2024.geobuddies.domain.map.repository.IGetFriendsGeoRepository
import com.surf2024.geobuddies.domain.map.repository.ISaveUserGeoRepository
import com.surf2024.geobuddies.domain.map.services.IGetFriendsGeoService
import com.surf2024.geobuddies.domain.map.services.ISaveUserGeoService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GeoModule {

    @Provides
    @Singleton
    fun provideGetFriendsGeoService(
        @Named("withJwt") retrofit: Retrofit
    ): IGetFriendsGeoService {
        Log.d("Hilt", "Creating IGetFriendsGeoService Retrofit client instance")
        return retrofit.create(IGetFriendsGeoService::class.java)
    }

    @Provides
    @Singleton
    fun provideSaveUserGeoService(
        @Named("withJwt") retrofit: Retrofit
    ): ISaveUserGeoService {
        Log.d("Hilt", "Creating ISaveUserGeoService Retrofit client instance")
        return retrofit.create(ISaveUserGeoService::class.java)
    }

    @Provides
    @Singleton
    fun provideGetFriendsGeoRepository(
        getFriendsGeoService: IGetFriendsGeoService
    ): IGetFriendsGeoRepository {
        Log.d("Hilt", "Creating GetFriendsGeoRepositoryImpl instance")
        return GetFriendsGeoRepositoryImpl(getFriendsGeoService)
    }

    @Provides
    @Singleton
    fun provideSaveUserGeoRepository(
        saveUserGeoService: ISaveUserGeoService
    ): ISaveUserGeoRepository {
        Log.d("Hilt", "Creating SaveUserGeoRepositoryImpl instance")
        return SaveUserGeoRepositoryImpl(saveUserGeoService)
    }
}
