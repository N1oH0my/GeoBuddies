package com.surf2024.geobuddies.domain.invites.entities

data class InviteModel (
    val id: Int,
    val name: String,
    val email: String,
    val avatarUrl: String,
    val birthDay: String,
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as InviteModel

        if (id != other.id) return false
        if (name != other.name) return false
        if (email != other.email) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result = 31 * result + email.hashCode()
        return result
    }
}