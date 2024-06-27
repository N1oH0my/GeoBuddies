package com.surf2024.geobuddies.data.friendsearch.repositoryimpl

import com.surf2024.geobuddies.domain.friendsearch.repository.IInviteSendRepository
import com.surf2024.geobuddies.domain.friendsearch.services.IInviteSendService
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class InviteSendRepositoryImpl @Inject constructor(
    private val inviteService: IInviteSendService
) : IInviteSendRepository {

    override fun inviteFriend(userId: Int): Completable {
        return inviteService.sendInvite(userId)
            .subscribeOn(Schedulers.io())
    }

}