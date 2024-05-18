package com.surf2024.geobuddies.domain.login.repositoryimpl

import com.surf2024.geobuddies.databinding.FragmentLoginBinding
import com.surf2024.geobuddies.domain.login.repository.FieldType
import com.surf2024.geobuddies.domain.login.repository.IInputLayoutFactoryRepository
import com.surf2024.geobuddies.domain.login.repository.ITextInputLayoutRepository

class InputLayoutFactoryRepositoryImpl: IInputLayoutFactoryRepository {
    override fun createRepository(binding: FragmentLoginBinding, fieldType: FieldType): ITextInputLayoutRepository {
        return when (fieldType) {
            FieldType.EMAIL -> EmailInputLayoutRepositoryImpl(binding)
            FieldType.PASSWORD -> PasswordInputLayoutRepositoryImpl(binding)
        }
    }
}