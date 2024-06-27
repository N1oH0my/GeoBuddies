package com.surf2024.geobuddies.data.map.repositoryimpl

import com.surf2024.geobuddies.domain.map.entity.FriendGeoModel
import com.surf2024.geobuddies.domain.map.repository.IGetFriendsGeoRepository
import com.surf2024.geobuddies.domain.map.services.IGetFriendsGeoService
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class GetFriendsGeoRepositoryImpl @Inject constructor(
    private val getFriendsGeoService: IGetFriendsGeoService
) : IGetFriendsGeoRepository {

    override fun getFriendsGeo(): Single<List<FriendGeoModel>> {
        return getFriendsGeoService.getFriendsGeo().subscribeOn(Schedulers.io()).map { response ->
                response ?: emptyList()
            }.onErrorResumeNext { throwable: Throwable ->
                Single.error(Throwable("Failed to get friends geo", throwable))
            }
    }

}