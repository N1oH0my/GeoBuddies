package com.surf2024.geobuddies.domain.login.services

import com.surf2024.geobuddies.domain.login.entity.LoginEntity
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ILoginService {
    @POST("/api/v1/auth/authenticate")
    fun login(@Body login: LoginEntity): Single<Response<Unit>>
}