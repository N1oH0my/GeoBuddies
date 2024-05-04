package com.surf2024.geobuddies.data.registration.module

import com.surf2024.geobuddies.data.registration.repositoryimpl.RegistrationRepositoryImpl
import com.surf2024.geobuddies.domain.registration.repository.RegistrationRepository
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
    fun provideRegistrationRepository(
        retrofit: Retrofit
    ):RegistrationRepository{
        return retrofit.create(RegistrationRepository::class.java)
    }

    @Provides
    @Singleton
    fun provideRegistrationRepositoryImpl(
        registrationRepository: RegistrationRepository
    ): RegistrationRepositoryImpl {
        return RegistrationRepositoryImpl(registrationRepository)
    }

}