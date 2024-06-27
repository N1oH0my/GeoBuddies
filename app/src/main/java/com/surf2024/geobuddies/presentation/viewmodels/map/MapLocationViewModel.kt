package com.surf2024.geobuddies.presentation.viewmodels.map

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.surf2024.geobuddies.domain.map.entity.FriendGeoModel
import com.surf2024.geobuddies.domain.map.entity.UserGeoModel
import com.surf2024.geobuddies.domain.map.repository.ICurrentUserLocationRepository
import com.surf2024.geobuddies.domain.map.repository.IGetFriendsGeoRepository
import com.surf2024.geobuddies.domain.map.repository.ISaveUserGeoRepository
import com.surf2024.geobuddies.domain.map.utility.ILocationPermissionChecker
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class MapLocationViewModel @Inject constructor(
    private val locationPermissionChecker: ILocationPermissionChecker,
    private val currentLocationRepository: ICurrentUserLocationRepository,
    private val saveUserGeoRepository: ISaveUserGeoRepository,
    private val getFriendsGeoRepository: IGetFriendsGeoRepository,
) : ViewModel() {

    private val userGeoDisposable: CompositeDisposable = CompositeDisposable()
    private val friendsGeoDisposable: CompositeDisposable = CompositeDisposable()

    private val _currentUserGeo = MutableLiveData<UserGeoModel>()
    val currentUserGeo: LiveData<UserGeoModel>
        get() = _currentUserGeo

    private val _userGeoSaved = MutableLiveData<Boolean>()
    val userGeoSaved: LiveData<Boolean>
        get() = _userGeoSaved

    private val _friendsGeoList = MutableLiveData<List<FriendGeoModel>>()
    val friendsGeoList: LiveData<List<FriendGeoModel>>
        get() = _friendsGeoList

    private val _alertDialogForLocationPermissionsNeeded = MutableLiveData<Boolean>()
    val alertDialogForLocationPermissionsNeeded: LiveData<Boolean>
        get() = _alertDialogForLocationPermissionsNeeded

    private val _requestLocationPermissionsNeeded = MutableLiveData<Boolean>()
    val requestLocationPermissionsNeeded: LiveData<Boolean>
        get() = _requestLocationPermissionsNeeded

    private val _serverError = MutableLiveData<Boolean>()
    val serverError: LiveData<Boolean>
        get() = _serverError

    private val _getUserGeoFailure = MutableLiveData<Boolean>()
    val getUserGeoFailure: LiveData<Boolean>
        get() = _getUserGeoFailure

    private val _permissionsFailure = MutableLiveData<Boolean>()
    val permissionsFailure: LiveData<Boolean>
        get() = _permissionsFailure

    fun getCurrentUserLocation() {
        currentLocationRepository.requestLocation({ location ->
            setCurrentUserGeo(longitude = location.longitude, latitude = location.latitude)
        }, {
            notifyPermissionsFailure()
        }, {
            notifyGetUserGeoFailure()
        })
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

    fun saveUserGeo(latitude: Double, longitude: Double) {
        userGeoDisposable.clear()
        val disposable = saveUserGeoRepository.saveGeo(latitude = latitude, longitude = longitude)
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                Log.d("GeoProccess", "Save successful")
                setUserGeoSaved()
            }, { error ->
                Log.d("GeoProccess", "Get failed $error")
                if (error is HttpException) {
                    Log.d("GeoProccess", "Get failed ${error.code()}")
                } else {
                    Log.d("GeoProccess", "Get failed ${error.message}")
                }
                setServerError()
            })
        userGeoDisposable.add(disposable)
    }

    fun getFriendsGeo() {
        friendsGeoDisposable.clear()
        val disposable =
            getFriendsGeoRepository.getFriendsGeo().observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    Log.d("GeoProccess", "Get friends geo successful $result")
                    setFriendsGeo(result)
                }, { error ->
                    Log.d("GeoProccess", "Get failed $error")
                    if (error is HttpException) {
                        Log.d("GeoProccess", "Get failed ${error.code()}")
                    } else {
                        Log.d("GeoProccess", "Get failed ${error.message}")
                    }
                    setServerError()
                })
        friendsGeoDisposable.add(disposable)
    }

    private fun setCurrentUserGeo(latitude: Double, longitude: Double) {
        _currentUserGeo.value = UserGeoModel(latitude = latitude, longitude = longitude)
    }

    private fun setUserGeoSaved() {
        _userGeoSaved.value = true
    }

    private fun setFriendsGeo(data: List<FriendGeoModel>) {
        _friendsGeoList.value = data
    }

    private fun notifyGetUserGeoFailure() {
        if(_getUserGeoFailure.value != true){
            _getUserGeoFailure.postValue(true)
        }
    }

    private fun notifyPermissionsFailure() {
        if(_permissionsFailure.value != true){
            _permissionsFailure.postValue(true)
        }
    }

    private fun notifyAlertDialogForLocationPermissionsNeeded() {
        _alertDialogForLocationPermissionsNeeded.value = true
    }

    private fun notifyRequestLocationPermissionsNeeded() {
        _requestLocationPermissionsNeeded.value = true
    }

    private fun setServerError(){
        if (_serverError.value != true){
            _serverError.value = true
        }
    }

}