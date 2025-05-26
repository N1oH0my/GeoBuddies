package com.surf2024.geobuddies.presentation.viewmodels.map

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.surf2024.geobuddies.domain.map.entity.GetAvatarResponseModel
import com.surf2024.geobuddies.domain.map.repository.IAvatarRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class CustomMapPinViewModel @Inject constructor(
    private val avatarRepository: IAvatarRepository
) : ViewModel() {

    private val avatarDisposable = CompositeDisposable()

    private val _isGetAvatarSuccess = MutableLiveData<GetAvatarResponseModel>()
    val getAvatarSuccess: LiveData<GetAvatarResponseModel>
        get() = _isGetAvatarSuccess

    private val _serverError = MutableLiveData<Boolean>()
    val serverError: LiveData<Boolean>
        get() = _serverError

    fun getAvatar(url: String): GetAvatarResponseModel {
        return avatarRepository.getAvatar(url).blockingGet()
        avatarDisposable.clear()
        val disposable = avatarRepository.getAvatar(url)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ byteArray ->
                Log.d("GetAvatarProcess", "Get successful: $byteArray")
                setGetAvatarSuccess(byteArray)
            }, { error ->
                Log.e("GetAvatarProcess", "Get failed", error)
                if (error is HttpException) {
                    Log.d("GetAvatarProcess", "HTTP Error: ${error.code()}")
                } else {
                    Log.d("GetAvatarProcess", "Error: ${error.message}")
                }
                setServerError()
            })
        avatarDisposable.add(disposable)
    }

    private fun setGetAvatarSuccess(result: GetAvatarResponseModel) {
        _isGetAvatarSuccess.value = result
    }

    private fun setServerError() {
        _serverError.value = true
    }

}