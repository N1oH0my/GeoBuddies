package com.surf2024.geobuddies.data.map.repositoryimpl
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.surf2024.geobuddies.domain.map.repository.ICurrentLocationRepository
import com.surf2024.geobuddies.domain.map.utility.ILocationPermissionChecker

class CurrentCurrentLocationRepositoryImpl(
    private val context: Context,
    private val locationPermissionChecker: ILocationPermissionChecker
): ICurrentLocationRepository {

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
