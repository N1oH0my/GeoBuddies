package com.surf2024.geobuddies.domain.invites.services

import io.reactivex.rxjava3.core.Completable
import retrofit2.http.POST
import retrofit2.http.Path

interface IAcceptInviteService {
    @POST("/api/v1/friend/invite/accepted/{userId}")
    fun acceptInvite(
        @Path("userId") userId: Int
    ): Completable
}