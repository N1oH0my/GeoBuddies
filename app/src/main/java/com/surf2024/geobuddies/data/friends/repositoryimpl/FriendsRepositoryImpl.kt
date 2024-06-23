package com.surf2024.geobuddies.data.friends.repositoryimpl

import com.surf2024.geobuddies.domain.friends.entity.FriendModel
import com.surf2024.geobuddies.domain.friends.repository.IFriendsRepository
import com.surf2024.geobuddies.domain.friends.services.IFriendsService
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class FriendsRepositoryImpl(
    private val friendsService: IFriendsService
): IFriendsRepository {
    override fun getAllFriends(): Single<List<FriendModel>> {
        return friendsService.getFriends()
            .subscribeOn(Schedulers.io())
            .map { response ->
                response ?: emptyList()
            }
            .onErrorResumeNext{
                Single.error(Throwable("Failed to get friends ${it.message}"))
            }
    }
}