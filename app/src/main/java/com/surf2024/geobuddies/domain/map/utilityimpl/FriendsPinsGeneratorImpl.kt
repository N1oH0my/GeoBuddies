package com.surf2024.geobuddies.domain.map.utilityimpl

import com.surf2024.geobuddies.domain.friends.entitity.FriendModel
import com.surf2024.geobuddies.domain.map.entity.FriendGeoModel
import com.surf2024.geobuddies.domain.map.entity.FriendPinModel
import com.surf2024.geobuddies.domain.map.utility.IFriendsPinsGenerator

class FriendsPinsGeneratorImpl(): IFriendsPinsGenerator {
    /**
     * Generates a list of FriendPinModel objects based on provided friends and their geolocation.
     *
     * @param friendList A list of FriendModel objects providing information about friends.
     * @param friendsGeoList List of FriendGeoModel objects that provide geodata of friends.
     * @param onSuccess A callback function that will be called with the generated list of FriendPinModel objects
     * after successful completion.
     * @param onDifferentSizesFailure A callback function that will be called if the sizes of friendList and friendsGeoList
     * do not match, this can happen if someone has accepted your friend request and load new geo
     * or if one friend delete you and new friend accept invite less then geo refresh.
     * @param onFailure A callback function that will be called if an exception occurs during processing.
     */
    override fun generateFriendsPins(
        friendList: List<FriendModel>,
        friendsGeoList: List<FriendGeoModel>,

        onSuccess: (List<FriendPinModel>) -> Unit,
        onDifferentSizesFailure: () -> Unit,
        onFailure: () -> Unit
    ){
        try {
            val friendPinsList = mutableListOf<FriendPinModel>()

            if ((friendList.size >= friendsGeoList.size)) {

                val friendMap = friendList.associateBy { it.id }

                friendsGeoList.forEach { friendGeoModel ->

                    val foundFriendModel = friendMap[friendGeoModel.userId]

                    if (foundFriendModel != null) {
                        val friendPinModel = FriendPinModel(
                            userId = foundFriendModel.id,
                            nickname = foundFriendModel.name,
                            avatarUrl = foundFriendModel.avatarUrl ?: "",

                            longitude = friendGeoModel.longitude,
                            latitude = friendGeoModel.latitude,
                        )
                        friendPinsList.add(friendPinModel)
                    } else{
                        onDifferentSizesFailure()
                    }
                }
            } else{
                onDifferentSizesFailure()
            }

            onSuccess(friendPinsList.toList())

        } catch (e: SecurityException) {
            e.printStackTrace()
            onFailure()
        }
    }
}