package com.surf2024.geobuddies.domain.map.repository

import com.surf2024.geobuddies.domain.map.entity.FriendPinModel
import com.surf2024.geobuddies.domain.map.entity.UserGeoModel

interface IMapPinsDrawer {
    fun friendsReload(data: List<FriendPinModel>)
    fun userReload(data: UserGeoModel)
}