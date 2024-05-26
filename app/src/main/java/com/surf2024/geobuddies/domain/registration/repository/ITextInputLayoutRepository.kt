package com.surf2024.geobuddies.domain.registration.repository

import com.google.android.material.textfield.TextInputLayout

interface ITextInputLayoutRepository {
    fun getInputLayout(): TextInputLayout
}