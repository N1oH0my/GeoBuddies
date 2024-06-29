package com.surf2024.geobuddies.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.surf2024.geobuddies.domain.common.repository.ITokenRepository
import com.surf2024.geobuddies.domain.login.repository.IAccessTokenSaver
import com.surf2024.geobuddies.domain.login.repository.IRefreshTokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class RefreshTokenViewModel @Inject constructor(
    private val refreshTokenRepository: IRefreshTokenRepository,
    private val accessTokenSaver: IAccessTokenSaver,
    private val tokenProvider: ITokenRepository,
): ViewModel() {

    private val refreshTokenDisposable = CompositeDisposable()

    private val _newAccessToken = MutableLiveData<String>()
    val newAccessToken: LiveData<String>
        get() = _newAccessToken

    private val _accessTokenSaved = MutableLiveData<Boolean>()
    val accessTokenSaved: LiveData<Boolean>
        get() = _accessTokenSaved

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean>
        get() = _error

    fun refreshAccessToken() {
        refreshTokenDisposable.clear()
        val disposable = refreshTokenRepository.refreshAccessToken()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ accessToken ->
                if (accessToken.isSuccessful) {
                    if (accessToken.body()?.accessToken != null) {
                        Log.d("RefreshProcess", "Get successful: ${accessToken.body()?.accessToken}")
                        setNewAccessToken(accessToken.body()!!.accessToken)
                    } else {
                        setError()
                    }
                } else {
                    Log.e("RefreshProcess", "Failed to get access token")
                    setError()
                }
            },
            { error ->
                Log.e("RefreshProcess", "Get failed", error)

                if (error is HttpException) {
                    Log.d("RefreshProcess", "HTTP Error: ${error.code()}")
                }
                else {
                    Log.d("RefreshProcess", "Error: ${error.message}")
                }
                setError()
            })
        refreshTokenDisposable.add(disposable)
    }

    fun saveAccessToken(accessToken: String) {
        if (accessTokenSaver.saveAccessToken(accessToken = accessToken)) {
            setAccessTokenSavedSuccessful()
        } else {
            setError()
        }
    }

    private fun setNewAccessToken(response: String){
        _newAccessToken.value = response
    }

    private fun setAccessTokenSavedSuccessful(){
        _accessTokenSaved.value = true
    }

    private fun setError(){
        if (_error.value != true){
            _error.value = true
        }
    }

}