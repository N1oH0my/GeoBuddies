package com.surf2024.geobuddies.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.surf2024.geobuddies.domain.login.repository.ILoginRepository
import com.surf2024.geobuddies.domain.login.repositoryimpl.LoginAccessTokenSaverRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: ILoginRepository,
    private val loginAccessTokenSaverRepository: LoginAccessTokenSaverRepositoryImpl
) : ViewModel() {

    private val disposables = CompositeDisposable()

    private val _isLoginSuccess = MutableLiveData<Boolean>()

    val isLoginSuccess: LiveData<Boolean>
        get() = _isLoginSuccess

    private fun setLoginSuccess(isSuccess: Boolean) {
        _isLoginSuccess.value = isSuccess
    }

    fun login(email: String?, password: String?) {
        disposables.clear()
        val disposable = loginRepository.login(email, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                if (response.isSuccessful) {
                    // TODO save all info
                    if (loginAccessTokenSaverRepository.saveAccessToken(response)) {
                        Log.d("loginProcess", "Access token saved successfully")
                    } else {
                        Log.e("loginProcess", "Failed to save access token")
                    }
                    setLoginSuccess(true)
                } else {
                    Log.e("loginProcess", "login failed: ${response.code()}")
                    setLoginSuccess(false)
                }
            }, { error ->
                Log.e("loginProcess", "login failed", error)

                if (error is HttpException) {
                    Log.d("loginProcess", "HTTP Error: ${error.code()}")
                } else {
                    Log.d("loginProcess", "Error: ${error.message}")
                }
                setLoginSuccess(false)
            })
        disposables.add(disposable)
    }


    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}