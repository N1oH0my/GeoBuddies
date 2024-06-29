package com.surf2024.geobuddies.data.common.repositoryimpl

import android.content.SharedPreferences
import com.surf2024.geobuddies.domain.common.entity.UserInfoKeys.ACCESS_TOKEN_KEY
import com.surf2024.geobuddies.domain.common.entity.UserInfoKeys.REFRESH_TOKEN_KEY
import com.surf2024.geobuddies.domain.common.repository.ITokenRepository
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val encryptedSharedPreferences: SharedPreferences
) : ITokenRepository {

    override fun getAccessToken(): String? {
        return encryptedSharedPreferences.getString(ACCESS_TOKEN_KEY, null)
    }

    override fun getRefreshToken(): String? {
        return encryptedSharedPreferences.getString(REFRESH_TOKEN_KEY, null)
    }

}