package com.surf2024.geobuddies.domain.login.repository

import com.surf2024.geobuddies.databinding.FragmentLoginBinding

interface IInputLayoutFactoryRepository {
    fun createRepository(binding: FragmentLoginBinding, fieldType: FieldType): ITextInputLayoutRepository
}

enum class FieldType {
    EMAIL,
    PASSWORD,
}