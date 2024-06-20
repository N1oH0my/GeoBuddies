package com.surf2024.geobuddies.domain.map.services

import com.surf2024.geobuddies.domain.map.entity.UserGeoModel
import io.reactivex.rxjava3.core.Completable
import retrofit2.http.Body
import retrofit2.http.POST


interface ISaveUserGeoService {
    @POST("/api/v1/geo/save")
    fun saveGeo(@Body userGeo: UserGeoModel): Completable
}