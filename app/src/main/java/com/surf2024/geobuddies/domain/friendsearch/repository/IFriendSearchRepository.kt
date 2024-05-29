package com.surf2024.geobuddies.domain.friendsearch.repository

import com.surf2024.geobuddies.domain.friendsearch.entity.FoundFriendModel
import io.reactivex.rxjava3.core.Single

interface IFriendSearchRepository {
    fun findFriend(userNameOrEmail: String): Single<List<FoundFriendModel>>
}