package com.surf2024.geobuddies.data.login.repositoryimpl

import android.content.SharedPreferences
import android.util.Log
import com.surf2024.geobuddies.domain.common.entity.UserInfoKeys
import com.surf2024.geobuddies.domain.login.repository.ITokensSaver
import javax.inject.Inject

class TokensSaverImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) : ITokensSaver {

    override fun saveAccessToken(accessToken: String): Boolean {
        return try {
            Log.d("encryptedSharedPreferences", "Saving access token: $accessToken")
            with(sharedPreferences.edit()) {
                putString(UserInfoKeys.ACCESS_TOKEN_KEY, accessToken)
                apply()
            }
            true
        } catch (e: Exception) {
            Log.e("encryptedSharedPreferences", "Error saving access token", e)
            false
        }
    }

    override fun saveRefreshToken(refreshToken: String): Boolean {
        return try {
            Log.d("encryptedSharedPreferences", "Saving access token: $refreshToken")
            with(sharedPreferences.edit()) {
                putString(UserInfoKeys.REFRESH_TOKEN_KEY, refreshToken)
                apply()
            }
            true
        } catch (e: Exception) {
            Log.e("encryptedSharedPreferences", "Error saving access token", e)
            false
        }
    }

}