package com.surf2024.geobuddies.domain.map.services

import com.surf2024.geobuddies.domain.map.entity.FriendGeoModel
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET

interface IGetFriendsGeoService{
    @GET("/api/v1/geo/friends/get")
    fun getFriendsGeo(): Single<Response<List<FriendGeoModel>>>
}
