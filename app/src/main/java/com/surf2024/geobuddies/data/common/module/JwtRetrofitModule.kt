package com.surf2024.geobuddies.data.common.module

import com.surf2024.geobuddies.BuildConfig
import com.surf2024.geobuddies.domain.common.repository.ITokenRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object JwtRetrofitModule {

    @Provides
    @Singleton
    @Named("withJwt")
    fun provideRetrofit(tokenProvider: ITokenRepository): Retrofit {
        val jwtInterceptor = Interceptor { chain ->
            if (tokenProvider.getAccessToken() != null) {
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer ${tokenProvider.getAccessToken()}")
                    .build()
                chain.proceed(request)
            } else {
                chain.proceed(chain.request())
            }
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(jwtInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @Named("withRefreshJwt")
    fun provideRefreshRetrofit(tokenProvider: ITokenRepository): Retrofit {
        val jwtInterceptor = Interceptor { chain ->
            if (tokenProvider.getRefreshToken() != null) {
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer ${tokenProvider.getRefreshToken()}")
                    .build()
                chain.proceed(request)
            } else {
                chain.proceed(chain.request())
            }
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(jwtInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}