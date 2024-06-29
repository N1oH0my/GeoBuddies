package com.surf2024.geobuddies.data.login.repositoryimpl

import com.surf2024.geobuddies.domain.login.entity.AccessTokenResponse
import com.surf2024.geobuddies.domain.login.repository.IRefreshAccessTokenRepository
import com.surf2024.geobuddies.domain.login.services.IRefreshAccessTokenService
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Response
import javax.inject.Inject

class RefreshAccessTokenRepositoryImpl @Inject constructor(
    private val refreshTokenService: IRefreshAccessTokenService
) : IRefreshAccessTokenRepository {

    override fun refreshAccessToken(): Single<Response<AccessTokenResponse>> {
        return refreshTokenService.refreshToken()
            .subscribeOn(Schedulers.io())
    }

}