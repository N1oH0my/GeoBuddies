package com.surf2024.geobuddies.data.login.repositoryimpl

import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.surf2024.geobuddies.domain.common.entity.UserInfoKeys.ACCESS_TOKEN_KEY
import com.surf2024.geobuddies.domain.common.entity.UserInfoKeys.REFRESH_TOKEN_KEY
import com.surf2024.geobuddies.domain.common.entity.UserInfoKeys.USER_INFO_KEY
import com.surf2024.geobuddies.domain.login.entity.LoginResponse
import com.surf2024.geobuddies.domain.login.entity.UserInfoModel
import com.surf2024.geobuddies.domain.login.repository.ILoginResponseSaver
import retrofit2.Response
import javax.inject.Inject

class LoginResponseSaverImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson,
) : ILoginResponseSaver {
    override fun saveUserTokensAndInfo(response: Response<LoginResponse>?): Boolean {
        if (response == null || !response.isSuccessful) return false

        val loginResponse = response.body() ?: return false

        Log.d("encryptedSharedPreferences", "LoginResponse get successful.")
        return try {
            Log.d("encryptedSharedPreferences", "Saving access token: ${loginResponse.accessToken}")
            Log.d("encryptedSharedPreferences", "Saving refresh token: ${loginResponse.refreshToken}")
            Log.d("encryptedSharedPreferences", "Saving user info: name = ${loginResponse.name}, email = ${loginResponse.email}, avatarUrl = ${loginResponse.avatarUrl}")
            val userInfo = UserInfoModel(
                name = loginResponse.name ?: "",
                email = loginResponse.email ?: "",
                avatarUrl = loginResponse.avatarUrl ?: ""
            )
            with(sharedPreferences.edit()) {
                putString(ACCESS_TOKEN_KEY, loginResponse.accessToken)
                putString(REFRESH_TOKEN_KEY, loginResponse.refreshToken)
                putString(USER_INFO_KEY, gson.toJson(userInfo))
                apply()
            }
            true
        } catch (e: Exception) {
            Log.e("encryptedSharedPreferences", "Error saving access token", e)
            false
        }

    }

}