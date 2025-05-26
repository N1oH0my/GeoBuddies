package com.surf2024.geobuddies.domain.map.services

import com.surf2024.geobuddies.domain.map.entity.FriendGeoModel
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface IGetFriendsGeoService {
    @GET("/api/users/geo/getFriendsGeo/")
    fun getFriendsGeo(): Single<List<FriendGeoModel>>

}
