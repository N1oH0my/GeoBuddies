package com.surf2024.geobuddies.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.surf2024.geobuddies.domain.map.entity.FriendGeoModel
import com.surf2024.geobuddies.domain.map.repository.IGetFriendsGeoRepository
import com.surf2024.geobuddies.domain.map.repository.ISaveUserGeoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class MapViewModel@Inject constructor(
    private val getFriendsGeoRepository: IGetFriendsGeoRepository,
    private val saveUserGeoRepository: ISaveUserGeoRepository,
): ViewModel(){
    private val friendsDisposable: CompositeDisposable = CompositeDisposable()
    private val userGeoDisposable: CompositeDisposable = CompositeDisposable()

    private val _friendsGeoList = MutableLiveData <List<FriendGeoModel>?>()
    val friendsGeoList: LiveData<List<FriendGeoModel>?>
        get() = _friendsGeoList
    private fun setFriendsGeo(data: List<FriendGeoModel>?){
        _friendsGeoList.value = data
    }

    private val _userGeo = MutableLiveData <Boolean>()
    val userGeo: LiveData<Boolean>
        get() = _userGeo
    private fun setUserGeo(data: Boolean){
        _userGeo.value = data
    }

    fun getFriendsGeo(){
        friendsDisposable.clear()
        val disposable = getFriendsGeoRepository.getFriendsGeo()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                Log.d("GeoProccess", "Get friends geo successful $result")
                setFriendsGeo(result)
            },{ error ->
                Log.d("GeoProccess", "Get failed $error")
                if(error is HttpException){
                    Log.d("GeoProccess", "Get failed ${error.code()}")
                }else{
                    Log.d("GeoProccess", "Get failed ${error.message}")
                }
                setFriendsGeo(null)
            })
        friendsDisposable.add(disposable)
    }
    fun saveUserGeo(latitude: Double, longitude: Double){
        userGeoDisposable.clear()
        val disposable = saveUserGeoRepository.saveGeo(latitude = latitude, longitude = longitude)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("GeoProccess", "Save successful")
                setUserGeo(true)
            },{ error ->
                Log.d("GeoProccess", "Get failed $error")
                if(error is HttpException){
                    Log.d("GeoProccess", "Get failed ${error.code()}")
                }else{
                    Log.d("GeoProccess", "Get failed ${error.message}")
                }
                setUserGeo(false)
            })
        userGeoDisposable.add(disposable)
    }
}