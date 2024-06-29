package com.surf2024.geobuddies.data.login.repositoryimpl

import android.content.SharedPreferences
import android.util.Log
import com.surf2024.geobuddies.domain.common.entity.UserInfoKeys
import com.surf2024.geobuddies.domain.login.repository.IAccessTokenSaver
import javax.inject.Inject

class AccessTokenSaverImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) : IAccessTokenSaver {

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

}