package com.surf2024.geobuddies.domain.registration.repository

import com.surf2024.geobuddies.domain.registration.entity.RegistrationModel
import io.reactivex.rxjava3.core.Single
import retrofit2.Response

interface IRegistrationRepository {
    fun register(registration: RegistrationModel): Single<Response<Unit>>
}