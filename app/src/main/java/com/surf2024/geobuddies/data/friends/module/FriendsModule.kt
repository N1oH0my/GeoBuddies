package com.surf2024.geobuddies.data.friends.module

import android.util.Log
import com.surf2024.geobuddies.data.friends.repositoryimpl.FriendDeleteRepositoryImpl
import com.surf2024.geobuddies.data.friends.repositoryimpl.FriendsRepositoryImpl
import com.surf2024.geobuddies.domain.friends.repository.IFriendDeleteRepository
import com.surf2024.geobuddies.domain.friends.repository.IFriendsRepository
import com.surf2024.geobuddies.domain.friends.services.IFriendDeleteService
import com.surf2024.geobuddies.domain.friends.services.IFriendsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FriendsModule {

    @Provides
    @Singleton
    fun provideFriendsService(
        @Named("withJwt") retrofit: Retrofit
    ): IFriendsService {
        Log.d("Hilt", "Creating IFriendsService Retrofit client instance")
        return retrofit.create(IFriendsService::class.java)
    }

    @Provides
    @Singleton
    fun provideFriendDeleteService(
        @Named("withJwt") retrofit: Retrofit
    ): IFriendDeleteService {
        Log.d("Hilt", "Creating IFriendDeleteService Retrofit client instance")
        return retrofit.create(IFriendDeleteService::class.java)
    }

    @Provides
    @Singleton
    fun provideFriendsRepositoryImpl(
        friendsService: IFriendsService
    ): IFriendsRepository {
        Log.d("Hilt", "Creating FriendsRepositoryImpl client instance")
        return FriendsRepositoryImpl(friendsService)
    }

    @Provides
    @Singleton
    fun provideFriendDeleteRepositoryImpl(
        friendDeleteService: IFriendDeleteService
    ): IFriendDeleteRepository {
        Log.d("Hilt", "Creating FriendDeleteRepositoryImpl client instance")
        return FriendDeleteRepositoryImpl(friendDeleteService)
    }

}