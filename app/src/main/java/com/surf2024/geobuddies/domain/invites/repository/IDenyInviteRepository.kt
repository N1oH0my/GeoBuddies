package com.surf2024.geobuddies.domain.invites.repository

import io.reactivex.rxjava3.core.Single
import retrofit2.Response

interface IDenyInviteRepository {
    fun denyInvite(userId: Int): Single<Response<Unit>>
}