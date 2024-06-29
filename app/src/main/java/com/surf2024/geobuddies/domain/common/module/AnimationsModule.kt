package com.surf2024.geobuddies.domain.common.module

import android.util.Log
import com.surf2024.geobuddies.domain.common.utility.IButtonAnimationHelper
import com.surf2024.geobuddies.domain.common.utilityimpl.ButtonAnimationHelperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AnimationsModule {

    @Provides
    @Singleton
    fun provideButtonAnimationHelper() : IButtonAnimationHelper {
        Log.d("Hilt", "Creating ButtonAnimationHelperImpl instance")
        return ButtonAnimationHelperImpl()
    }

}