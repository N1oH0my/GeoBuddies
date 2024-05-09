package com.surf2024.geobuddies.data.registration.repositoryimpl

import com.surf2024.geobuddies.domain.registration.entity.RegistrationModel
import com.surf2024.geobuddies.domain.registration.repository.IRegistrationRepository
import com.surf2024.geobuddies.domain.registration.services.IRegistrationService
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Response
import javax.inject.Inject


class RegistrationRepositoryImpl @Inject constructor(
    private val registrationService: IRegistrationService
): IRegistrationRepository {
    override suspend fun register(registration: RegistrationModel): Single<Response<Unit>> {
        return registrationService.register(registration).subscribeOn(Schedulers.io())
    }
}