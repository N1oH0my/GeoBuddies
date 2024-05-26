package com.surf2024.geobuddies.data.login.module

import android.util.Log
import com.surf2024.geobuddies.data.login.repositoryimpl.LoginRepositoryImpl
import com.surf2024.geobuddies.domain.login.repository.IInputLayoutFactoryRepository
import com.surf2024.geobuddies.domain.login.repository.ILoginRepository
import com.surf2024.geobuddies.domain.login.repository.ILoginValidatorRepository
import com.surf2024.geobuddies.domain.login.repositoryimpl.InputLayoutFactoryRepositoryImpl
import com.surf2024.geobuddies.domain.login.repositoryimpl.LoginValidatorRepositoryImpl
import com.surf2024.geobuddies.domain.login.services.ILoginService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoginModule {
    @Provides
    @Singleton
    fun provideLoginService(
        retrofit: Retrofit
    ): ILoginService {
        Log.d("Hilt", "Creating ILoginService client instance")
        return retrofit.create(ILoginService::class.java)
    }

    @Provides
    @Singleton
    fun provideLoginRepositoryImpl(
        loginService: ILoginService,
        loginValidator: ILoginValidatorRepository
    ): ILoginRepository {
        Log.d("Hilt", "Creating LoginRepositoryImpl client instance")
        return LoginRepositoryImpl(loginService, loginValidator)
    }

    @Provides
    fun provideInputLayoutFactory(): IInputLayoutFactoryRepository {
        Log.d("Hilt", "Creating InputLayoutRepositoryFactoryImpl client instance")
        return InputLayoutFactoryRepositoryImpl()
    }

    @Provides
    fun provideLoginValidator(): ILoginValidatorRepository {
        Log.d("Hilt", "Creating LoginValidator client instance")
        return LoginValidatorRepositoryImpl()
    }
}