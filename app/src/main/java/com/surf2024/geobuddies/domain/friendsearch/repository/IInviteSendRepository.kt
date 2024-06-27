package com.surf2024.geobuddies.domain.friendsearch.repository
import io.reactivex.rxjava3.core.Completable

interface IInviteSendRepository {
    fun inviteFriend(userId: Int): Completable

}