package com.surf2024.geobuddies.domain.registration.repository

interface IRegistrationValidatorRepository {
    fun validateRegistrationFields(
        email: String?,
        password: String?,
        confirmedPassword: String?,
        name: String?,
    ): Boolean
}