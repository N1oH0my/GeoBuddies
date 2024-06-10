package com.surf2024.geobuddies.domain.common.repository

interface ITokenProvider {
    fun getToken(): String?
}