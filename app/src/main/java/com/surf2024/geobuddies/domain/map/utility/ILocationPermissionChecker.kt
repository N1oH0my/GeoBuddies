package com.surf2024.geobuddies.domain.map.utility

import androidx.fragment.app.Fragment

interface ILocationPermissionChecker {
    fun isLocationPermissionGranted(): Boolean
    fun shouldShowLocationPermissionRationale(fragment: Fragment): Boolean

}