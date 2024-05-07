package com.surf2024.geobuddies.domain.registration.repository

import com.surf2024.geobuddies.domain.registration.entity.RegistrationEntity
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
interface RegistrationRepository {

    @POST("/api/v1/auth/register")
    suspend fun register(@Body registration: RegistrationEntity): Call<Void>
}