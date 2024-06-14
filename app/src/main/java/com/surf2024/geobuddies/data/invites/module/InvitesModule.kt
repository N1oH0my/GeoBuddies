package com.surf2024.geobuddies.data.invites.module

import android.util.Log
import com.surf2024.geobuddies.data.invites.repositoryimpl.AcceptInviteRepositoryImpl
import com.surf2024.geobuddies.data.invites.repositoryimpl.DenyInviteRepositoryImpl
import com.surf2024.geobuddies.data.invites.repositoryimpl.InvitesRepositoryImpl
import com.surf2024.geobuddies.domain.invites.repository.IAcceptInviteRepository
import com.surf2024.geobuddies.domain.invites.repository.IDenyInviteRepository
import com.surf2024.geobuddies.domain.invites.repository.IInvitesRepository
import com.surf2024.geobuddies.domain.invites.services.IAcceptInviteService
import com.surf2024.geobuddies.domain.invites.services.IDenyInviteService
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
    fun provideAcceptInviteService(
        @Named("withJwt") retrofit: Retrofit
    ): IAcceptInviteService {
        Log.d("Hilt", "Creating IAcceptInviteService Retrofit client instance")
        return retrofit.create(IAcceptInviteService::class.java)
    }

    @Provides
    @Singleton
    fun provideDenyInviteService(
        @Named("withJwt") retrofit: Retrofit
    ): IDenyInviteService {
        Log.d("Hilt", "Creating IDenyInviteService Retrofit client instance")
        return retrofit.create(IDenyInviteService::class.java)
    }

    @Provides
    @Singleton
    fun provideInvitesRepositoryImpl(
        invitesService: IInvitesService
    ): IInvitesRepository{
        Log.d("Hilt", "Creating InvitesRepositoryImpl client instance")
        return InvitesRepositoryImpl(invitesService)
    }

    @Provides
    @Singleton
    fun provideAcceptInviteRepositoryImpl(
        acceptInviteService: IAcceptInviteService
    ): IAcceptInviteRepository{
        Log.d("Hilt", "Creating AcceptInviteRepositoryImpl client instance")
        return AcceptInviteRepositoryImpl(acceptInviteService)
    }

    @Provides
    @Singleton
    fun provideDenyInviteRepositoryImpl(
        denyInviteService: IDenyInviteService
    ): IDenyInviteRepository {
        Log.d("Hilt", "Creating DenyInviteRepositoryImpl client instance")
        return DenyInviteRepositoryImpl(denyInviteService)
    }
}