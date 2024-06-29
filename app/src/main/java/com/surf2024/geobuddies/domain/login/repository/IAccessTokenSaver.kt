package com.surf2024.geobuddies.domain.login.repository

interface IAccessTokenSaver {

    fun saveAccessToken(accessToken: String): Boolean

}