package com.surf2024.geobuddies.domain.map.utility

import com.surf2024.geobuddies.domain.friends.entity.FriendModel
import com.surf2024.geobuddies.domain.map.entity.FriendGeoModel
import com.surf2024.geobuddies.domain.map.entity.FriendPinModel

interface IFriendsPinsGenerator {

    fun generateFriendsPins(
        friendList: List<FriendModel>,
        friendsGeoList: List<FriendGeoModel>,

        onSuccess: (List<FriendPinModel>) -> Unit,
        onDifferentSizesFailure: () -> Unit,
        onFailure: () -> Unit
    )

}