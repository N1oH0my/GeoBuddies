package com.surf2024.geobuddies.domain.main.usecase

import androidx.fragment.app.Fragment

interface FragmentChangeListener {
    fun onRegistrationComplete()
    fun onRegistrationBack()
    fun onLoginComplete()
    fun onSignUpClicked()

    fun onFriendsClose()
    fun onFriendsSearchClose()
    fun onInvitesClose()
    fun onMapClose()

    fun openFriends()
    fun openFriendsSearch()
    fun openInvites()

    fun onLogOut()
}