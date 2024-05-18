package com.surf2024.geobuddies.domain.login.repositoryimpl

import com.surf2024.geobuddies.domain.login.repository.ILoginValidatorRepository
import javax.inject.Inject

class LoginValidatorRepositoryImpl @Inject constructor(): ILoginValidatorRepository {
    override fun validateLoginFields(
        email: String?,
        password: String?,
    ): Boolean {
        return email != null && password != null
    }
}