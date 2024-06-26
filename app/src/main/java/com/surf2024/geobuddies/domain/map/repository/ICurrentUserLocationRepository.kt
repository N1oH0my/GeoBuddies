package com.surf2024.geobuddies.domain.map.repository

import android.location.Location

interface ICurrentUserLocationRepository {
    fun requestLocation(onSuccess: (Location) -> Unit, onPermissionsFailure: () -> Unit, onFailure: () -> Unit)
}