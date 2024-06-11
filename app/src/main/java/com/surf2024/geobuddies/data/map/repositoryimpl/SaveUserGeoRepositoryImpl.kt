package com.surf2024.geobuddies.data.map.repositoryimpl

import com.surf2024.geobuddies.domain.map.entity.UserGeoModel
import com.surf2024.geobuddies.domain.map.repository.ISaveUserGeoRepository
import com.surf2024.geobuddies.domain.map.services.ISaveUserGeoService
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Response
import javax.inject.Inject

class SaveUserGeoRepositoryImpl @Inject constructor(
    private val saveUserGeoService : ISaveUserGeoService
): ISaveUserGeoRepository {
    override fun saveGeo(
        longitude: String,
        latitude: String,
    ): Single<Response<Unit>> {
        return saveUserGeoService.saveGeo(
            UserGeoModel(longitude = longitude, latitude = latitude,))
            .subscribeOn(Schedulers.io())
    }
}