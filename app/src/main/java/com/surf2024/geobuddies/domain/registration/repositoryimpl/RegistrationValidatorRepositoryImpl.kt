package com.surf2024.geobuddies.domain.registration.repositoryimpl

import com.surf2024.geobuddies.domain.registration.repository.IRegistrationValidatorRepository
import javax.inject.Inject

class RegistrationValidatorRepositoryImpl @Inject constructor(): IRegistrationValidatorRepository {
    override fun validateRegistrationFields(
        email: String?,
        password: String?,
        confirmedPassword: String?,
        name: String?,
    ): Boolean {
        if (name != null && email != null && password != null && confirmedPassword != null) {
            if (password == confirmedPassword) {
                return true
            }
        }
        return false
    }
}