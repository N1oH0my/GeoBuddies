package com.surf2024.geobuddies.data.invites.module

import android.util.Log
import com.surf2024.geobuddies.data.invites.repositoryimpl.InvitesRepositoryImpl
import com.surf2024.geobuddies.domain.invites.repository.IInvitesRepository
import com.surf2024.geobuddies.domain.invites.services.IInvitesService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InvitesModule {

    @Provides
    @Singleton
    fun provideInvitesService(
        @Named("withJwt") retrofit: Retrofit
    ): IInvitesService {
        Log.d("Hilt", "Creating IInvitesService Retrofit client instance")
        return retrofit.create(IInvitesService::class.java)
    }

    @Provides
    @Singleton
    fun provideInvitesRepositoryImpl(
        invitesService: IInvitesService
    ): IInvitesRepository{
        Log.d("Hilt", "Creating InvitesRepositoryImpl Retrofit client instance")
        return InvitesRepositoryImpl(invitesService)
    }
}