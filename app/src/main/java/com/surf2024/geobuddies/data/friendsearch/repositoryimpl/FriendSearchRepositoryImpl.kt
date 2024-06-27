package com.surf2024.geobuddies.data.friendsearch.repositoryimpl

import com.surf2024.geobuddies.domain.friendsearch.entity.FoundFriendModel
import com.surf2024.geobuddies.domain.friendsearch.repository.IFriendSearchRepository
import com.surf2024.geobuddies.domain.friendsearch.services.IFriendSearchService
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class FriendSearchRepositoryImpl @Inject constructor(
    private val searchService: IFriendSearchService
) : IFriendSearchRepository {

    override fun findFriend(userNameOrEmail: String): Single<List<FoundFriendModel>> {
        return searchService.findFriend(userNameOrEmail).subscribeOn(Schedulers.io())
            .flatMap { response ->
                if (response.isSuccessful) {
                    Single.just(response.body() ?: emptyList())
                } else {
                    Single.error(Throwable("Failed to find friend"))
                }
            }
    }

}