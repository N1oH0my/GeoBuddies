package com.surf2024.geobuddies.domain.login.entity

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    val name: String,
    val email: String,
    val avatarUrl: String,
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("refresh_token") val refreshToken: String
)