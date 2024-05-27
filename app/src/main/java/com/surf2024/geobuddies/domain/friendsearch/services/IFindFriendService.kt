package com.surf2024.geobuddies.domain.friendsearch.services

import com.surf2024.geobuddies.domain.friendsearch.entity.FoundFriendModel
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface IFindFriendService {
    @GET("/api/v1/user/find/{userNameOrEmail}")
    fun findFriend(
        @Path("userNameOrEmail") userNameOrEmail: String
    ): Single<Response<List<FoundFriendModel>>>
}
