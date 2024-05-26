package com.surf2024.geobuddies.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.surf2024.geobuddies.domain.registration.repository.IRegistrationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registrationRepository: IRegistrationRepository
) : ViewModel() {

    private val disposables = CompositeDisposable()

    private val _isRegistrationSuccess = MutableLiveData<Boolean>()


    val isRegistrationSuccess: LiveData<Boolean>
        get() = _isRegistrationSuccess
        private fun setRegistrationSuccess(isSuccess: Boolean) {
            _isRegistrationSuccess.value = isSuccess
        }

    fun register(
        email: String?,
        password: String?,
        confirmedPassword: String?,
        name: String?,
        ) {
        disposables.clear()
        val disposable = registrationRepository.register(
            email,
            password,
            confirmedPassword,
            name
        )
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ isSuccess ->
            Log.d("RegistrationProcess", "Registration successful: $isSuccess")
            setRegistrationSuccess(true)
        }, { error ->
            Log.e("RegistrationProcess", "Registration failed", error)

            if (error is HttpException) {
                Log.d("RegistrationProcess", "HTTP Error: ${error.code()}")
            }
            else {
                Log.d("RegistrationProcess", "Error: ${error.message}")
            }
            setRegistrationSuccess(false)
        })
        disposables.add(disposable)
    }


    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}