package com.surf2024.geobuddies.domain.map.module

import android.util.Log
import com.surf2024.geobuddies.domain.map.utility.IFriendsPinsGenerator
import com.surf2024.geobuddies.domain.map.utilityimpl.FriendsPinsGeneratorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UtilityModule {

    @Provides
    @Singleton
    fun provideFriendsPinsGeneratorImpl() : IFriendsPinsGenerator {
        Log.d("Hilt", "Creating FriendsPinsGeneratorImpl instance")
        return FriendsPinsGeneratorImpl()
    }

}