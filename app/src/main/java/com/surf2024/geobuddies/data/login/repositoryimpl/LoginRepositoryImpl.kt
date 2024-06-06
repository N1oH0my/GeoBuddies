package com.surf2024.geobuddies.data.login.repositoryimpl

import com.surf2024.geobuddies.domain.login.entity.LoginEntity
import com.surf2024.geobuddies.domain.login.entity.LoginResponse
import com.surf2024.geobuddies.domain.login.repository.ILoginRepository
import com.surf2024.geobuddies.domain.login.repository.ILoginValidatorRepository
import com.surf2024.geobuddies.domain.login.services.ILoginService
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Response
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginService: ILoginService,
    private val loginValidator: ILoginValidatorRepository,
) : ILoginRepository {
    override fun login(
        email: String?,
        password: String?
    ): Single<Response<LoginResponse>> {

        if (email == null || password == null) {
            return Single.error(Throwable("Email or password cannot be null"))
        }

        val isValid = loginValidator.validateLoginFields(email, password)
        return if (isValid) {
            loginService.login(
                LoginEntity(
                    email = email,
                    password = password
                )
            )
        } else {
            Single.error(Throwable("Invalid registration fields"))
        }
    }

}