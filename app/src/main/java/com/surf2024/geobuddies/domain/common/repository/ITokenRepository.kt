package com.surf2024.geobuddies.domain.common.repository

interface ITokenRepository {
    fun getAccessToken(): String?

    fun getRefreshToken(): String?

}