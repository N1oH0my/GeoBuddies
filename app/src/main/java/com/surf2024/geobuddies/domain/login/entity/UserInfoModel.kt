package com.surf2024.geobuddies.domain.login.entity

import com.google.gson.annotations.SerializedName

data class UserInfoModel(
    @SerializedName("user_name") val name: String,
    @SerializedName("user_email") val email: String,
    @SerializedName("user_avatar_url") val avatarUrl: String,
)