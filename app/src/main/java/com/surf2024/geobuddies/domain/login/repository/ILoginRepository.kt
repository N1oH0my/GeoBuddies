package com.surf2024.geobuddies.domain.login.repository

import io.reactivex.rxjava3.core.Single
import retrofit2.Response

interface ILoginRepository {
    fun login(
        email: String?,
        password: String?,
        ): Single<Response<Unit>>
}