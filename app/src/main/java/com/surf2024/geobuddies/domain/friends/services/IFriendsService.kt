package com.surf2024.geobuddies.domain.friends.services

import com.surf2024.geobuddies.domain.friends.entitity.FriendModel
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface IFriendsService {
    @GET("/api/v1/friend/get/myfriens")
    fun getFriends(): Single<List<FriendModel>>
}