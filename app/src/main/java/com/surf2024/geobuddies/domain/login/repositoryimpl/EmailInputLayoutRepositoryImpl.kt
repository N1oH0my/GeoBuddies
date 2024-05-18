package com.surf2024.geobuddies.domain.login.repositoryimpl

import com.google.android.material.textfield.TextInputLayout
import com.surf2024.geobuddies.databinding.FragmentLoginBinding
import com.surf2024.geobuddies.domain.login.repository.ITextInputLayoutRepository

class EmailInputLayoutRepositoryImpl(private val binding: FragmentLoginBinding):
    ITextInputLayoutRepository {
    override fun getInputLayout(): TextInputLayout {
        return binding.loginFragmentEmailTextInput
    }
}