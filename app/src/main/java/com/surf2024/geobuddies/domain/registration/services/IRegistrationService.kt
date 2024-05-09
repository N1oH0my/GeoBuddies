package com.surf2024.geobuddies.domain.registration.services

import com.surf2024.geobuddies.domain.registration.entity.RegistrationModel
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface IRegistrationService {
    @POST("/api/v1/auth/register")
    fun register(@Body registration: RegistrationModel): Single<Response<Unit>>
}
