package com.surf2024.geobuddies.data.invites.repositoryimpl

import com.surf2024.geobuddies.domain.invites.repository.IAcceptInviteRepository
import com.surf2024.geobuddies.domain.invites.services.IAcceptInviteService
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class AcceptInviteRepositoryImpl@Inject constructor(
    private val acceptInviteService: IAcceptInviteService
): IAcceptInviteRepository {
    override fun acceptInvite(userId: Int): Completable {
        return acceptInviteService.acceptInvite(userId)
            .subscribeOn(Schedulers.io())
    }
}