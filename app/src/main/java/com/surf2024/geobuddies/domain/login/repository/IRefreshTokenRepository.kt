package com.surf2024.geobuddies.domain.login.repository

import com.surf2024.geobuddies.domain.login.entity.AccessTokenResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.Response

interface IRefreshTokenRepository {

    fun refreshAccessToken(): Single<Response<AccessTokenResponse>>

}