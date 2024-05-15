package com.surf2024.geobuddies.domain.registration.repositoryimpl

import com.google.android.material.textfield.TextInputLayout
import com.surf2024.geobuddies.databinding.FragmentRegistrationBinding
import com.surf2024.geobuddies.domain.registration.repository.ITextInputLayoutRepository

class PasswordInputLayoutRepositoryImpl(private val binding: FragmentRegistrationBinding): ITextInputLayoutRepository {
    override fun getInputLayout(): TextInputLayout {
        return binding.passwordRegistrationTextInput
    }
}

class ConfirmPasswordInputLayoutRepositoryImpl(private val binding: FragmentRegistrationBinding): ITextInputLayoutRepository {
    override fun getInputLayout(): TextInputLayout {
        return binding.confirmpasswordRegistrationTextInput
    }
}