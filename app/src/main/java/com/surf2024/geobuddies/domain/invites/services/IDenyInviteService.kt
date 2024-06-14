package com.surf2024.geobuddies.domain.invites.services

import io.reactivex.rxjava3.core.Completable
import retrofit2.http.POST
import retrofit2.http.Path

interface IDenyInviteService {
    @POST("/api/v1/friend/invite/decline/{userId}")
    fun denyInvite(
        @Path("userId") userId: Int
    ): Completable
}