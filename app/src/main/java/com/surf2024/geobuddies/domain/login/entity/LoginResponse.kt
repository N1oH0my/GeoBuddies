package com.surf2024.geobuddies.domain.login.entity

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    // TODO new model
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("refresh_token") val refreshToken: String
)