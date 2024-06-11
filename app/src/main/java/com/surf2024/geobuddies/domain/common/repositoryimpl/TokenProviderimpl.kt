package com.surf2024.geobuddies.domain.common.repositoryimpl

import android.content.SharedPreferences
import com.surf2024.geobuddies.domain.common.entity.TokenKeys.ACCESS_TOKEN_KEY
import com.surf2024.geobuddies.domain.common.repository.ITokenProvider
import javax.inject.Inject

class TokenProviderimpl @Inject constructor(
    private val encryptedSharedPreferences: SharedPreferences
): ITokenProvider {
    override fun getToken(): String? {
        return encryptedSharedPreferences.getString(ACCESS_TOKEN_KEY, null)
    }
}