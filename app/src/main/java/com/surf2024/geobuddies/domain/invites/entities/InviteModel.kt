package com.surf2024.geobuddies.domain.invites.entities

data class InviteModel (
    val id: Int,
    val name: String,
    val email: String,
    val avatarUrl: String,
    val birthDay: String,
)