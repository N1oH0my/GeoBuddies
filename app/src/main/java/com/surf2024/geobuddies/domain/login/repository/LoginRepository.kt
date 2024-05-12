package com.surf2024.geobuddies.domain.login.repository

import com.surf2024.geobuddies.domain.login.entity.LoginEntity
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginRepository {
    @POST("/api/v1/auth/authenticate")
    suspend fun login(@Body login: LoginEntity): Call<Void>
}