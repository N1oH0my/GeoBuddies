package com.surf2024.geobuddies.data.map.repositoryimpl
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.surf2024.geobuddies.domain.map.repository.ICurrentUserLocationRepository
import com.surf2024.geobuddies.domain.map.utility.ILocationPermissionChecker

class CurrentUserUserLocationRepositoryImpl(
    private val context: Context,
    private val locationPermissionChecker: ILocationPermissionChecker
): ICurrentUserLocationRepository {

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    override fun requestLocation(onSuccess: (Location) -> Unit, onPermissionsFailure: () -> Unit, onFailure: () -> Unit) {
        if (locationPermissionChecker.isLocationPermissionGranted()) {
            try {
                fusedLocationClient.getCurrentLocation(
                    Priority.PRIORITY_BALANCED_POWER_ACCURACY,
                    CancellationTokenSource().token
                )
                    .addOnSuccessListener { location: Location? ->
                        if (location != null) {
                            Log.d("UserLocation", "${location.latitude}, ${location.longitude}")
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
