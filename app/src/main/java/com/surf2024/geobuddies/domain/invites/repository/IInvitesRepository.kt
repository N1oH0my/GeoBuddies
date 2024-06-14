package com.surf2024.geobuddies.domain.invites.repository

import com.surf2024.geobuddies.domain.invites.entities.InviteModel
import io.reactivex.rxjava3.core.Single
import retrofit2.Response

interface IInvitesRepository {
    fun getAllInvites(): Single<List<InviteModel>>
}