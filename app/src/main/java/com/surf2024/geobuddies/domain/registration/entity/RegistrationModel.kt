package com.surf2024.geobuddies.domain.registration.entity

data class RegistrationModel(
    val email: String,
    val password: String,
    val birthDate: String = "",
    val name: String,
)
