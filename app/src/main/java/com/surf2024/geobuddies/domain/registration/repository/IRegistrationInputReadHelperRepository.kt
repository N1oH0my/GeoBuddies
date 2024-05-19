package com.surf2024.geobuddies.domain.registration.repository

interface IRegistrationInputReadHelperRepository {
    fun getName(): String?
    fun getEmail(): String?
    fun getPassword(): String?
    fun getConfirmedPassword(): String?
}