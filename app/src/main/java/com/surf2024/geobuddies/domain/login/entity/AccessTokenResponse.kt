package com.surf2024.geobuddies.domain.login.entity

import com.google.gson.annotations.SerializedName
import com.surf2024.geobuddies.domain.common.entity.UserInfoKeys.ACCESS_TOKEN_KEY

data class AccessTokenResponse(
    @SerializedName(ACCESS_TOKEN_KEY) val accessToken: String
)
