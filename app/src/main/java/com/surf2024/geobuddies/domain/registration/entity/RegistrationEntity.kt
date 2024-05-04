package com.surf2024.geobuddies.domain.registration.entity

import java.time.LocalDate

data class RegistrationEntity(
    val email: String,
    val name: String,
    val password: String,
    val birthDate: LocalDate,
)
