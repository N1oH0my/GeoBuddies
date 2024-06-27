package com.surf2024.geobuddies.domain.friendsearch.services

import io.reactivex.rxjava3.core.Completable
import retrofit2.http.POST
import retrofit2.http.Path

interface IInviteSendService {
    @POST("/api/v1/friend/invite/{userId}")
    fun sendInvite(
        @Path("userId") userId: Int
    ): Completable

}