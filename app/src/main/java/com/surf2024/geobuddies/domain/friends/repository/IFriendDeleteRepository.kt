package com.surf2024.geobuddies.domain.friends.repository

import io.reactivex.rxjava3.core.Completable

interface IFriendDeleteRepository {
    fun deleteFriend(friendId: Int): Completable
}