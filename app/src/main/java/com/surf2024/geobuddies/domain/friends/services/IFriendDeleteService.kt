package com.surf2024.geobuddies.domain.friends.services

import io.reactivex.rxjava3.core.Completable
import retrofit2.http.DELETE
import retrofit2.http.Path

interface IFriendDeleteService {
    @DELETE("/api/users/friends/delete/{userId}")
    fun deleteFriend(
        @Path("userId") friendId: Int
    ): Completable
}