package com.surf2024.geobuddies.domain.registration.repository

import com.surf2024.geobuddies.databinding.FragmentRegistrationBinding

interface IInputLayoutFactoryRepository {
    fun createRepository(binding: FragmentRegistrationBinding, fieldType: FieldType): ITextInputLayoutRepository
}

enum class FieldType {
    NAME,
    EMAIL,
    PASSWORD,
    CONFIRMEDPASSWORD,
}