package com.surf2024.geobuddies.domain.map.repository

import com.surf2024.geobuddies.domain.map.entity.FriendGeoModel
import com.surf2024.geobuddies.domain.map.entity.UserGeoModel

interface IMapPinsDrawer {
    fun friendsReload(data: List<FriendGeoModel>)
    fun userReload(data: UserGeoModel)
}