package com.surf2024.geobuddies.data.invites.repositoryimpl

import com.surf2024.geobuddies.domain.invites.repository.IDenyInviteRepository
import com.surf2024.geobuddies.domain.invites.services.IDenyInviteService
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class DenyInviteRepositoryImpl@Inject constructor(
    private val denyInviteService: IDenyInviteService
): IDenyInviteRepository {
    override fun denyInvite(userId: Int): Completable {
        return denyInviteService.denyInvite(userId)
            .subscribeOn(Schedulers.io())
    }
}