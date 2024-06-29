package com.surf2024.geobuddies.domain.login.repository

interface ITokensSaver {

    fun saveAccessToken(accessToken: String): Boolean

    fun saveRefreshToken(refreshToken: String): Boolean

}