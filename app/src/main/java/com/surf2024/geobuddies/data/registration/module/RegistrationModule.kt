package com.surf2024.geobuddies.data.registration.module

import com.surf2024.geobuddies.data.registration.repositoryimpl.RegistrationRepositoryImpl
import com.surf2024.geobuddies.domain.registration.repository.IRegistrationRepository
import com.surf2024.geobuddies.domain.registration.services.IRegistrationService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RegistrationModule {

    @Provides
    @Singleton
    fun provideRegistrationService(
        retrofit: Retrofit
    ): IRegistrationService {
        return retrofit.create(IRegistrationService::class.java)
    }

    @Provides
    @Singleton
    fun provideRegistrationRepositoryImpl(
        registrationService: IRegistrationService
    ): IRegistrationRepository {
        return RegistrationRepositoryImpl(registrationService)
    }
}