package com.surf2024.geobuddies.data.friendsearch.repositoryimpl

import com.surf2024.geobuddies.domain.friendsearch.repository.IInviteSendRepository
import com.surf2024.geobuddies.domain.friendsearch.services.IInviteSendService
import com.surf2024.geobuddies.domain.registration.entity.RegistrationModel
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Response
import javax.inject.Inject

class InviteSendRepositoryImpl@Inject constructor(
    private val inviteService: IInviteSendService
): IInviteSendRepository{
    override fun inviteFriend(userId: Int): Single<Response<Unit>> {
        return inviteService.sendInvite(userId)
            .subscribeOn(Schedulers.io())
    }

}