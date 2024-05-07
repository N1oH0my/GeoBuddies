package com.surf2024.geobuddies.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.surf2024.geobuddies.data.registration.repositoryimpl.RegistrationRepositoryImpl
import com.surf2024.geobuddies.domain.registration.entity.RegistrationEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MainVM @Inject constructor(
    private val registrationRepositoryImpl: RegistrationRepositoryImpl
): ViewModel() {

    private val _userNameLiveData = MutableLiveData<String>()
    val userNameLiveData: LiveData<String>
        get() = _userNameLiveData

    fun updateName(name: String) {
        _userNameLiveData.value = name
    }

    fun registration(){
        viewModelScope.launch(Dispatchers.IO) {
            registrationRepositoryImpl.registration(
                RegistrationEntity(
                "asd@mail.ru",
                "asd",
                "12331"
                )
            )
            TODO()
        }
    }

}