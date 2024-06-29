package com.surf2024.geobuddies.domain.main.usecase

interface RefreshAccessTokenListener {

    fun refreshAccessTokenSuccessful()

    fun refreshAccessTokenFailed()

}