package com.surf2024.geobuddies.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainVM: ViewModel() {

    private val _userNameLiveData = MutableLiveData<String>()
    val userNameLiveData: LiveData<String>
        get() = _userNameLiveData

    fun updateName(name: String) {
        _userNameLiveData.value = name
    }
}