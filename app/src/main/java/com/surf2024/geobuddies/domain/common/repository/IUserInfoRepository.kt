package com.surf2024.geobuddies.domain.common.repository

import com.surf2024.geobuddies.domain.login.entity.UserInfoModel

interface IUserInfoRepository {
    fun getUserInfo(): UserInfoModel?
}