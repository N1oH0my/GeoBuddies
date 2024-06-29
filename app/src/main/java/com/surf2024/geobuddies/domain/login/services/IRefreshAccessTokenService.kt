package com.surf2024.geobuddies.domain.login.services

import com.surf2024.geobuddies.domain.login.entity.AccessTokenResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.POST

interface IRefreshAccessTokenService {

    @POST("/api/v1/auth/refresh-token")
    fun refreshToken(): Single<Response<AccessTokenResponse>>

}