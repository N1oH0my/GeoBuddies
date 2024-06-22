package com.surf2024.geobuddies.domain.map.entity

data class FriendPinModel(
    val nickname: String,
    val avatarUrl: String,

    val longitude: Double,
    val latitude: Double,

    val userId: Int,
)