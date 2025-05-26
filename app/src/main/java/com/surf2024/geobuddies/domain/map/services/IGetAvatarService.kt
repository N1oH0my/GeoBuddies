package com.surf2024.geobuddies.domain.map.services

import com.surf2024.geobuddies.domain.map.entity.GetAvatarResponseModel
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface IGetAvatarService {

    @GET("/api/users/account/avatar/")
    fun getAvatar(@Query("imageUrl") imageUrl: String): Single<GetAvatarResponseModel>

}