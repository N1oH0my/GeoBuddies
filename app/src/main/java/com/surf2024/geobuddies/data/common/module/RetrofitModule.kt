package com.surf2024.geobuddies.data.common.module

import com.surf2024.geobuddies.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {
    @Provides
    @Singleton
    fun provideRetrofitClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(R.string.api_url.toString())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}