package com.surf2024.geobuddies.domain.invites.usecases

interface IOnInviteClickListener {
    fun onInviteAcceptClick(position: Int)
    fun onInviteDenyClick(position: Int)
}