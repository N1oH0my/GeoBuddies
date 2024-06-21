package com.surf2024.geobuddies.domain.map.utilityimpl

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.surf2024.geobuddies.domain.map.utility.ILocationPermissionChecker

class LocationPermissionChecker(private val context: Context): ILocationPermissionChecker {

    override fun isLocationPermissionGranted(): Boolean {
        return (
                ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    override fun shouldShowLocationPermissionRationale(fragment: Fragment): Boolean {
        return fragment.shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)
    }
}
