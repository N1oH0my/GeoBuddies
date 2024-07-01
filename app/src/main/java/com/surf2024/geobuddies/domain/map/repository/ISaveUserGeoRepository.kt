package com.surf2024.geobuddies.domain.map.repository

import io.reactivex.rxjava3.core.Completable


interface ISaveUserGeoRepository {
    fun saveGeo(
        longitude: Double,
        latitude: Double,
    ): Completable

}