package com.surf2024.geobuddies.domain.login.repository

import com.surf2024.geobuddies.domain.login.entity.LoginResponse
import retrofit2.Response

interface ILoginResponseSaver {
    fun saveUserTokensAndInfo(
        response: Response<LoginResponse>?
    ): Boolean
}