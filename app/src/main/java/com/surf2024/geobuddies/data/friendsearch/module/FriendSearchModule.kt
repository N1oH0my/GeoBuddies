package com.surf2024.geobuddies.data.friendsearch.module

import android.util.Log
import com.surf2024.geobuddies.data.friendsearch.repositoryimpl.FriendSearchRepositoryImpl
import com.surf2024.geobuddies.domain.friendsearch.repository.IFriendSearchRepository
import com.surf2024.geobuddies.domain.friendsearch.services.IFriendSearchService
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
        Log.d("Hilt", "Creating ISearchFriendService client instance")
        return retrofit.create(IFriendSearchService::class.java)
    }
    @Provides
    @Singleton
    fun provideFriendSearchRepositoryImpl(
        searchService: IFriendSearchService
    ): IFriendSearchRepository {
        Log.d("Hilt", "Creating IFriendSearchRepository client instance")
        return FriendSearchRepositoryImpl(searchService)
    }

}