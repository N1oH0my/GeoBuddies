package com.surf2024.geobuddies.data.registration.repositoryimpl

import com.surf2024.geobuddies.domain.registration.entity.RegistrationEntity
import com.surf2024.geobuddies.domain.registration.repository.RegistrationRepository
import retrofit2.Call
import javax.inject.Inject


class RegistrationRepositoryImpl @Inject constructor(
    private val registrationRepository: RegistrationRepository
) {
    suspend fun registration(registrationEntity: RegistrationEntity): Call<Void> {
        return registrationRepository.register(registrationEntity)
    }
}