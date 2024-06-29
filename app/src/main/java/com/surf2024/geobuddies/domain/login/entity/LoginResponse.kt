package com.surf2024.geobuddies.domain.login.entity

import com.google.gson.annotations.SerializedName
import com.surf2024.geobuddies.domain.common.entity.UserInfoKeys.ACCESS_TOKEN_KEY
import com.surf2024.geobuddies.domain.common.entity.UserInfoKeys.REFRESH_TOKEN_KEY

data class LoginResponse(
    val name: String,
    val email: String,
    val avatarUrl: String,
    @SerializedName(ACCESS_TOKEN_KEY) val accessToken: String,
    @SerializedName(REFRESH_TOKEN_KEY) val refreshToken: String
)