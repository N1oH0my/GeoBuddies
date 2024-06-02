package com.surf2024.geobuddies.domain.login.repository

interface ILoginAccessTokenSaverRepository {
    fun saveAccessToken(
        accessToken: String?
    ): Boolean
}