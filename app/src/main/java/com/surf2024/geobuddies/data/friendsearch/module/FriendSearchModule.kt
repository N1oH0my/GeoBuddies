package com.surf2024.geobuddies.data.friendsearch.module

import android.util.Log
import com.surf2024.geobuddies.data.friendsearch.repositoryimpl.FriendSearchRepositoryImpl
import com.surf2024.geobuddies.data.friendsearch.repositoryimpl.InviteSendRepositoryImpl
import com.surf2024.geobuddies.domain.friendsearch.repository.IFriendSearchRepository
import com.surf2024.geobuddies.domain.friendsearch.repository.IInviteSendRepository
import com.surf2024.geobuddies.domain.friendsearch.services.IFriendSearchService
import com.surf2024.geobuddies.domain.friendsearch.services.IInviteSendService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FriendSearchModule {

    @Provides
    @Singleton
    fun provideSearchFriendService(
        @Named("withJwt") retrofit: Retrofit
    ): IFriendSearchService {
        Log.d("Hilt", "Creating ISearchFriendService Retrofit client instance")
        return retrofit.create(IFriendSearchService::class.java)
    }

    @Provides
    @Singleton
    fun provideInviteFriendService(
        @Named("withJwt") retrofit: Retrofit
    ): IInviteSendService {
        Log.d("Hilt", "Creating IInviteSendService Retrofit client instance")
        return retrofit.create(IInviteSendService::class.java)
    }
    @Provides
    @Singleton
    fun provideFriendSearchRepositoryImpl(
        searchService: IFriendSearchService
    ): IFriendSearchRepository {
        Log.d("Hilt", "Creating FriendSearchRepositoryImpl client instance")
        return FriendSearchRepositoryImpl(searchService)
    }

    @Provides
    @Singleton
    fun provideFriendInviteRepositoryImpl(
        inviteService: IInviteSendService
    ): IInviteSendRepository {
        Log.d("Hilt", "Creating InviteSendRepositoryImpl client instance")
        return InviteSendRepositoryImpl(inviteService)
    }

}