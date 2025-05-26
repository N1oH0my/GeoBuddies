package com.surf2024.geobuddies.data.map.module

import android.util.Log
import com.surf2024.geobuddies.data.map.repositoryimpl.AvatarRepositoryImpl
import com.surf2024.geobuddies.domain.map.repository.IAvatarRepository
import com.surf2024.geobuddies.domain.map.services.IGetAvatarService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AvatarModule {

    @Provides
    @Singleton
    fun provideGetAvatarService(
        @Named("withJwt") retrofit: Retrofit
    ): IGetAvatarService {
        Log.d("Hilt", "Creating IGetAvatarService Retrofit client instance")
        return retrofit.create(IGetAvatarService::class.java)
    }

    @Provides
    @Singleton
    fun provideAvatarRepository(
        getAvatarService: IGetAvatarService
    ): IAvatarRepository {
        Log.d("Hilt", "Creating AvatarRepositoryImpl instance")
        return AvatarRepositoryImpl(getAvatarService)
    }

}