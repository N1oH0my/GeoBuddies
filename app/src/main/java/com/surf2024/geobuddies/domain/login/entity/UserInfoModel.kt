package com.surf2024.geobuddies.domain.login.entity

import com.google.gson.annotations.SerializedName
import com.surf2024.geobuddies.domain.common.entity.UserInfoKeys.USER_AVATAR_URL
import com.surf2024.geobuddies.domain.common.entity.UserInfoKeys.USER_EMAIL
import com.surf2024.geobuddies.domain.common.entity.UserInfoKeys.USER_NAME

data class UserInfoModel(
    @SerializedName(USER_NAME) val name: String,
    @SerializedName(USER_EMAIL) val email: String,
    @SerializedName(USER_AVATAR_URL) val avatarUrl: String,
)