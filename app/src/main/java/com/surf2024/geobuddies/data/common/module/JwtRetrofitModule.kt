package com.surf2024.geobuddies.data.common.module

import com.surf2024.geobuddies.BuildConfig
import com.surf2024.geobuddies.domain.common.repository.ITokenProvider
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
    fun provideRetrofit(tokenProvider: ITokenProvider): Retrofit {
        val jwtInterceptor = Interceptor { chain ->

            if (tokenProvider.getToken() != null) {
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer ${tokenProvider.getToken()}")
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