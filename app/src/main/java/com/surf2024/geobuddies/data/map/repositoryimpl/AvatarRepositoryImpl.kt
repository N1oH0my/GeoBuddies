package com.surf2024.geobuddies.data.map.repositoryimpl

import com.surf2024.geobuddies.domain.map.entity.GetAvatarResponseModel
import com.surf2024.geobuddies.domain.map.repository.IAvatarRepository
import com.surf2024.geobuddies.domain.map.services.IGetAvatarService
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class AvatarRepositoryImpl @Inject constructor(
    private val getAvatarService: IGetAvatarService
) : IAvatarRepository {

    override fun getAvatar(imageUrl: String): Single<GetAvatarResponseModel> {
        return getAvatarService.getAvatar(imageUrl)
    }

}