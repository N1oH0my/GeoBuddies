package com.surf2024.geobuddies.domain.login.repositoryimpl

import android.content.SharedPreferences
import android.util.Log
import com.surf2024.geobuddies.domain.login.entity.LoginResponse
import com.surf2024.geobuddies.domain.login.repository.ILoginAccessTokenSaverRepository
import retrofit2.Response
import javax.inject.Inject

class LoginAccessTokenSaverRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : ILoginAccessTokenSaverRepository {
    override fun saveAccessToken(response: Response<LoginResponse>?): Boolean {
        if (response == null) return false
        if (response.isSuccessful) {
            val loginResponse = response.body()
            val accessToken = loginResponse?.accessToken
            Log.d("LoginAccessTokenSaver", "login successful: Access Token = $accessToken")
            return try {
                with(sharedPreferences.edit()) {
                    putString(ACCESS_TOKEN_KEY, accessToken)
                    apply()
                }
                true
            } catch (e: Exception) {
                Log.e("LoginAccessTokenSaver", "Error saving access token", e)
                false
            }
        }
        return false
    }

    private companion object {
        const val ACCESS_TOKEN_KEY = "access_token"
    }
}