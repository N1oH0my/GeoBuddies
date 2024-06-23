package com.surf2024.geobuddies.data.friends.repositoryimpl

import com.surf2024.geobuddies.domain.friends.repository.IFriendDeleteRepository
import com.surf2024.geobuddies.domain.friends.services.IFriendDeleteService
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers

class FriendDeleteRepositoryImpl(
    private val friendDeleteService: IFriendDeleteService
): IFriendDeleteRepository {
    override fun deleteFriend(friendId: Int): Completable {
        return friendDeleteService.deleteFriend(friendId)
            .subscribeOn(Schedulers.io())
    }
}