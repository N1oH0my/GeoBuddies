package com.surf2024.geobuddies.domain.map.repositoryimpl
import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.surf2024.geobuddies.domain.map.repository.ILocationRepository
import com.surf2024.geobuddies.domain.map.utilityimpl.LocationPermissionChecker

class LocationRepositoryImpl(
    private val context: Context,
    private val locationPermissionChecker: LocationPermissionChecker
): ILocationRepository {

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    override fun requestLocation(onSuccess: (Location) -> Unit, onPermissionsFailure: () -> Unit, onFailure: () -> Unit) {
        if (locationPermissionChecker.isLocationPermissionGranted()) {
            try {
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location: Location? ->
                        if (location != null) {
                            onSuccess(location)
                        } else {
                            onFailure()
                        }
                    }
                    .addOnFailureListener {
                        onFailure()
                    }
            } catch (e: SecurityException) {
                e.printStackTrace()
                onFailure()
            }
        } else {
            onPermissionsFailure()
        }
    }
}
