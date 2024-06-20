package com.surf2024.geobuddies.data.map.repositoryimpl

import com.surf2024.geobuddies.domain.map.entity.UserGeoModel
import com.surf2024.geobuddies.domain.map.repository.ISaveUserGeoRepository
import com.surf2024.geobuddies.domain.map.services.ISaveUserGeoService
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject


class SaveUserGeoRepositoryImpl @Inject constructor(
    private val saveUserGeoService : ISaveUserGeoService
): ISaveUserGeoRepository {
    override fun saveGeo(
        longitude: Double,
        latitude: Double,
    ): Completable {
        return saveUserGeoService.saveGeo(
            UserGeoModel(longitude = longitude, latitude = latitude)
        )
            .subscribeOn(Schedulers.io())
    }
}