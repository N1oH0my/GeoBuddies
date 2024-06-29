package com.surf2024.geobuddies.data.common.repositoryimpl

import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.surf2024.geobuddies.domain.common.entity.UserInfoKeys.USER_INFO_KEY
import com.surf2024.geobuddies.domain.common.repository.IUserInfoRepository
import com.surf2024.geobuddies.domain.login.entity.UserInfoModel
import javax.inject.Inject

class UserInfoRepositoryImpl @Inject constructor(
    private val encryptedSharedPreferences: SharedPreferences,
    private val gson: Gson
): IUserInfoRepository {
    override fun getUserInfo(): UserInfoModel? {
        return try {
            val json = encryptedSharedPreferences.getString(USER_INFO_KEY, null)
            if (json != null) {
                gson.fromJson(json, UserInfoModel::class.java)
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("encryptedSharedPreferences", "Error retrieving user data", e)
            null
        }
    }

}