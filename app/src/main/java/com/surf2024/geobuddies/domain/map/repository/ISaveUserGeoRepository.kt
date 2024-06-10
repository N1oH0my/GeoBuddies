package com.surf2024.geobuddies.domain.map.repository

import io.reactivex.rxjava3.core.Single

interface ISaveUserGeoRepository {
    fun saveGeo(
        longitude: String,
        latitude: String,
    ): Single<Unit>
}