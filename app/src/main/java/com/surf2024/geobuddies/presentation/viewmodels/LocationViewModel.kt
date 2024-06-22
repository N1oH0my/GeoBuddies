package com.surf2024.geobuddies.presentation.viewmodels

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.surf2024.geobuddies.domain.map.entity.UserGeoModel
import com.surf2024.geobuddies.domain.map.repository.ILocationRepository
import com.surf2024.geobuddies.domain.map.utility.ILocationPermissionChecker
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LocationViewModel@Inject constructor(
    private val locationPermissionChecker: ILocationPermissionChecker,
    private val locationRepository: ILocationRepository
): ViewModel() {

    private val _currentUserGeo = MutableLiveData<UserGeoModel>()
    val currentUserGeo: LiveData<UserGeoModel>
        get() = _currentUserGeo
    private fun setCurrentUserGeo(latitude: Double, longitude: Double){
        _currentUserGeo.value = UserGeoModel(latitude = latitude, longitude = longitude)
    }

    private val _permissionsFailure = MutableLiveData<Boolean>()
    val permissionsFailure: LiveData<Boolean>
        get() = _permissionsFailure
    private fun notifyPermissionsFailure(){
        _permissionsFailure.value = false
    }
    private val _getUserGeoFailure = MutableLiveData<Boolean>()
    val getUserGeoFailure: LiveData<Boolean>
        get() = _getUserGeoFailure
    private fun notifyGetUserGeoFailure(){
        _getUserGeoFailure.value = false
    }

    private val _alertDialogForLocationPermissionsNeeded = MutableLiveData<Boolean>()
    val alertDialogForLocationPermissionsNeeded: LiveData<Boolean>
        get() = _alertDialogForLocationPermissionsNeeded
    private fun notifyAlertDialogForLocationPermissionsNeeded(){
        _alertDialogForLocationPermissionsNeeded.value = true
    }

    private val _requestLocationPermissionsNeeded = MutableLiveData<Boolean>()
    val requestLocationPermissionsNeeded: LiveData<Boolean>
        get() = _requestLocationPermissionsNeeded
    private fun notifyRequestLocationPermissionsNeeded(){
        _requestLocationPermissionsNeeded.value = true
    }
    fun getLocation() {
        locationRepository.requestLocation(
            { location ->
                setCurrentUserGeo(longitude = location.longitude, latitude = location.latitude)
            }, {
                notifyPermissionsFailure()
            }, {
                notifyGetUserGeoFailure()
            }
        )
    }

    fun checkLocationPermission(fragment: Fragment) {
        if (!locationPermissionChecker.isLocationPermissionGranted()) {
            if (locationPermissionChecker.shouldShowLocationPermissionRationale(fragment)) {
                notifyAlertDialogForLocationPermissionsNeeded()
            } else {
                notifyRequestLocationPermissionsNeeded()
            }
        }
    }
}