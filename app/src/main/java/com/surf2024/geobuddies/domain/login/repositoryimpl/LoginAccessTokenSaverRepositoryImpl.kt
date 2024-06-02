package com.surf2024.geobuddies.domain.login.repositoryimpl

import android.content.SharedPreferences
import android.util.Log
import com.surf2024.geobuddies.domain.login.repository.ILoginAccessTokenSaverRepository
import javax.inject.Inject

class LoginAccessTokenSaverRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : ILoginAccessTokenSaverRepository {
    override fun saveAccessToken(accessToken: String?): Boolean {
        if (accessToken == null) return false

        return try {
            with(sharedPreferences.edit()) {
                putString("access_token", accessToken)
                apply()
            }
            true
        } catch (e: Exception) {
            Log.e("LoginAccessTokenSaver", "Error saving access token", e)
            false
        }
    }
}