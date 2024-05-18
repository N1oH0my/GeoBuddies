package com.surf2024.geobuddies.domain.login.repository

interface ILoginInputReadHelperRepository {
    fun getEmail(): String?
    fun getPassword(): String?
}