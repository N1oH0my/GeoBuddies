package com.surf2024.geobuddies.domain.login.repository

interface ILoginValidatorRepository {
    fun validateLoginFields(
        email: String?,
        password: String?,
    ): Boolean
}