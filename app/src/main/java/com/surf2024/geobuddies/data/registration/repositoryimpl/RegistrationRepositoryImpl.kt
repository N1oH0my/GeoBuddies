package com.surf2024.geobuddies.data.registration.repositoryimpl

import android.util.Log
import com.surf2024.geobuddies.domain.registration.entity.RegistrationModel
import com.surf2024.geobuddies.domain.registration.repository.IRegistrationRepository
import com.surf2024.geobuddies.domain.registration.repository.IRegistrationValidatorRepository
import com.surf2024.geobuddies.domain.registration.services.IRegistrationService
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Response
import javax.inject.Inject


class RegistrationRepositoryImpl @Inject constructor(
    private val registrationService: IRegistrationService,
    private val registrationValidator: IRegistrationValidatorRepository,
): IRegistrationRepository {
    override fun register(
        email: String?,
        password: String?,
        confirmedPassword: String?,
        name: String?,

    ): Single<Response<Unit>> {

        val isValid = registrationValidator.validateRegistrationFields(
            email,
            password,
            confirmedPassword,
            name
        )
        if (isValid) {
            return registrationService.register(
                RegistrationModel(
                    email = email!!,
                    password = password!!,
                    name = name!!
                )
            ).subscribeOn(Schedulers.io())
        }
        return Single.error(Throwable("Invalid registration fields"))
    }

}