package com.surf2024.geobuddies.data.login.module

import com.surf2024.geobuddies.data.login.repositoryimpl.LoginRepositoryImpl
import com.surf2024.geobuddies.domain.login.repository.LoginRepository
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
    fun provideLoginRepository(
        retrofit: Retrofit
    ): LoginRepository {
        return retrofit.create(LoginRepository::class.java)
    }

    @Provides
    @Singleton
    fun provideLoginRepositoryImpl(
        loginRepository: LoginRepository
    ): LoginRepositoryImpl {
        return LoginRepositoryImpl(loginRepository)
    }
}