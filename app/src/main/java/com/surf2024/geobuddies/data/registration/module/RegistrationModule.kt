package com.surf2024.geobuddies.data.registration.module

import android.util.Log
import com.surf2024.geobuddies.data.registration.repositoryimpl.RegistrationRepositoryImpl
import com.surf2024.geobuddies.domain.registration.repository.IInputLayoutFactoryRepository
import com.surf2024.geobuddies.domain.registration.repository.IRegistrationRepository
import com.surf2024.geobuddies.domain.registration.repository.IRegistrationValidatorRepository
import com.surf2024.geobuddies.domain.registration.repositoryimpl.InputLayoutFactoryRepositoryImpl
import com.surf2024.geobuddies.domain.registration.repositoryimpl.RegistrationValidatorRepositoryImpl
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
        Log.d("Hilt", "Creating IRegistrationService client instance")
        return retrofit.create(IRegistrationService::class.java)
    }

    @Provides
    @Singleton
    fun provideRegistrationRepositoryImpl(
        registrationService: IRegistrationService,
        registrationValidator: IRegistrationValidatorRepository
    ): IRegistrationRepository {
        Log.d("Hilt", "Creating RegistrationRepositoryImpl client instance")
        return RegistrationRepositoryImpl(registrationService, registrationValidator)
    }

    @Provides
    fun provideInputLayoutFactory(): IInputLayoutFactoryRepository {
        Log.d("Hilt", "Creating InputLayoutRepositoryFactoryImpl client instance")
        return InputLayoutFactoryRepositoryImpl()
    }

    @Provides
    fun provideRegistrationValidator(): IRegistrationValidatorRepository {
        Log.d("Hilt", "Creating RegistrationValidator client instance")
        return RegistrationValidatorRepositoryImpl()
    }

}