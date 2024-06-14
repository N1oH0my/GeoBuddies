package com.surf2024.geobuddies.domain.invites.repository

import io.reactivex.rxjava3.core.Completable

interface IAcceptInviteRepository {
    fun acceptInvite(userId: Int): Completable
}