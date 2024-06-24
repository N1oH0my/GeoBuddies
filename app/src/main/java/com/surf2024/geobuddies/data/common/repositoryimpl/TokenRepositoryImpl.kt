package com.surf2024.geobuddies.data.common.repositoryimpl

import android.content.SharedPreferences
import com.surf2024.geobuddies.domain.common.entity.TokenKeys.ACCESS_TOKEN_KEY
import com.surf2024.geobuddies.domain.common.repository.ITokenRepository
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val encryptedSharedPreferences: SharedPreferences
): ITokenRepository {
    override fun getToken(): String? {
        return encryptedSharedPreferences.getString(ACCESS_TOKEN_KEY, null)
    }
}