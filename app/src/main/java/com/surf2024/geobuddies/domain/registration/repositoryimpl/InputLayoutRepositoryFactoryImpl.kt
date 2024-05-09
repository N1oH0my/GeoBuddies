package com.surf2024.geobuddies.domain.registration.repositoryimpl

import com.surf2024.geobuddies.databinding.FragmentRegistrationBinding
import com.surf2024.geobuddies.domain.registration.repository.FieldType
import com.surf2024.geobuddies.domain.registration.repository.IInputLayoutFactory
import com.surf2024.geobuddies.domain.registration.repository.ITextInputLayoutRepository

class InputLayoutRepositoryFactoryImpl: IInputLayoutFactory {

    override fun createRepository(binding: FragmentRegistrationBinding, fieldType: FieldType): ITextInputLayoutRepository {
        return when (fieldType) {
            FieldType.NAME -> NameInputLayoutRepositoryImpl(binding)
            FieldType.EMAIL -> EmailInputLayoutRepositoryImpl(binding)
            FieldType.PASSWORD -> PasswordInputLayoutRepositoryImpl(binding)
            FieldType.CONFIRMEDPASSWORD -> ConfirmPasswordInputLayoutRepositoryImpl(binding)
        }
    }
}