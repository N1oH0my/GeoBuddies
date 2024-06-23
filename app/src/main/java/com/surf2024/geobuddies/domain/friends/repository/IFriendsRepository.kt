package com.surf2024.geobuddies.domain.friends.repository

import com.surf2024.geobuddies.domain.friends.entitity.FriendModel
import io.reactivex.rxjava3.core.Single

interface IFriendsRepository {
    fun getAllFriends(): Single<List<FriendModel>>
}