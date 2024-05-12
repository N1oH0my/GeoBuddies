package com.surf2024.geobuddies.data.login.repositoryimpl

import com.surf2024.geobuddies.domain.login.entity.LoginEntity
import com.surf2024.geobuddies.domain.login.repository.LoginRepository
import retrofit2.Call
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor (
    private val loginRepository: LoginRepository
) {
    suspend fun login(loginEntity: LoginEntity): Call<Void> {
        return loginRepository.login(loginEntity)
    }
}