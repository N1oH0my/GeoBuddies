package com.surf2024.geobuddies.domain.login.entity

data class LoginResponse(
    val id: Int,
    val name: String,
    val email: String,
    val avatarUrl: String?,
    val accessToken: String,
    val refreshToken: String
)