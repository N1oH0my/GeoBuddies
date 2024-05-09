package com.surf2024.geobuddies.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.surf2024.geobuddies.domain.registration.entity.RegistrationModel
import com.surf2024.geobuddies.domain.registration.repository.IRegistrationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registrationRepository: IRegistrationRepository
) : ViewModel() {

    private val disposables = CompositeDisposable()

    private val _isLoading = MutableLiveData<Boolean>()
    private val _isRegistrationSuccess = MutableLiveData<Boolean>()

    val isLoading: LiveData<Boolean>
        get() = _isLoading
        fun setLoading(isLoading: Boolean) {
            _isLoading.value = isLoading
        }

    val isRegistrationSuccess: LiveData<Boolean>
        get() = _isRegistrationSuccess
        private fun setRegistrationSuccess(isSuccess: Boolean) {
            _isRegistrationSuccess.value = isSuccess
        }

    fun register(registrationModel: RegistrationModel) {
        _isLoading.value = true
        disposables.add(
            Observable.create<Boolean> { emitter ->
                try {
                    val response = registrationRepository.register(registrationModel)
                    emitter.onNext(true)
                    emitter.onComplete()
                } catch (error: Throwable) {
                    emitter.onError(error)
                }
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ isSuccess ->
                    _isLoading.value = false
                    setRegistrationSuccess(isSuccess)
                }, { error ->
                    _isLoading.value = false
                    setRegistrationSuccess(false)
                })
        )
    }


    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}