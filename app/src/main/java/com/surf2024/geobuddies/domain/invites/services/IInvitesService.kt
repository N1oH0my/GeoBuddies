package com.surf2024.geobuddies.domain.invites.services

import com.surf2024.geobuddies.domain.invites.entities.InviteModel
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET

interface IInvitesService {
    @GET("/api/v1/friend/invite/getinvites")
    fun getAllInvites(): Single<Response<List<InviteModel>>>
}