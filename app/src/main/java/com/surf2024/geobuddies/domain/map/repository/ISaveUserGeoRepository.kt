package com.surf2024.geobuddies.domain.map.repository

import io.reactivex.rxjava3.core.Single
import retrofit2.Response

interface ISaveUserGeoRepository {
    fun saveGeo(
        longitude: String,
        latitude: String,
    ): Single<Response<Unit>>
}