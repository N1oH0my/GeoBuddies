package com.surf2024.geobuddies.domain.friendsearch.repository
import io.reactivex.rxjava3.core.Single
import retrofit2.Response

interface IInviteSendRepository {
    fun inviteFriend(userId: Int): Single<Response<Unit>>
}