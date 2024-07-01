package com.surf2024.geobuddies.domain.map.repository

import com.surf2024.geobuddies.domain.map.entity.FriendGeoModel
import io.reactivex.rxjava3.core.Single

interface IGetFriendsGeoRepository {
    fun getFriendsGeo(): Single<List<FriendGeoModel>>

}