package com.surf2024.geobuddies.domain.map.repository

import com.surf2024.geobuddies.domain.map.entity.GetAvatarResponseModel
import io.reactivex.rxjava3.core.Single

interface IAvatarRepository {

    fun getAvatar(imageUrl: String): Single<GetAvatarResponseModel>

}