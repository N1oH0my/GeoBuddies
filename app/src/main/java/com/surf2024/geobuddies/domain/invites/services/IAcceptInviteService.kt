package com.surf2024.geobuddies.domain.invites.services

import io.reactivex.rxjava3.core.Completable
import retrofit2.http.POST
import retrofit2.http.Path

interface IAcceptInviteService {
    @POST("/api/users/invites/accept/{senderId}")
    fun acceptInvite(
        @Path("senderId") userId: Int
    ): Completable
}