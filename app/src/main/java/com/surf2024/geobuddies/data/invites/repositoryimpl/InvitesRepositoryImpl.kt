package com.surf2024.geobuddies.data.invites.repositoryimpl

import com.surf2024.geobuddies.domain.invites.entities.InviteModel
import com.surf2024.geobuddies.domain.invites.repository.IInvitesRepository
import com.surf2024.geobuddies.domain.invites.services.IInvitesService
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class InvitesRepositoryImpl @Inject constructor(
    private val invitesService: IInvitesService
): IInvitesRepository {
    override fun getAllInvites(): Single<List<InviteModel>> {
        return invitesService.getAllInvites()
            .subscribeOn(Schedulers.io())
            .map { response ->
                response ?: emptyList()
                }
            .onErrorResumeNext{
                    Single.error(Throwable("Failed to get invites", it))
                }
    }

}