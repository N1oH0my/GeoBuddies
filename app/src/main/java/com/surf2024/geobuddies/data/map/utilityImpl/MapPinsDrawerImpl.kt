package com.surf2024.geobuddies.data.map.utilityImpl

import android.content.Context
import android.graphics.PointF
import android.view.View
import com.surf2024.geobuddies.R
import com.surf2024.geobuddies.domain.map.entity.FriendPinModel
import com.surf2024.geobuddies.domain.map.entity.UserGeoModel
import com.surf2024.geobuddies.domain.map.utility.IMapPinsDrawer
import com.surf2024.geobuddies.presentation.feature.CustomMapPinView
import com.yandex.mapkit.Animation
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.mapkit.map.TextStyle
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import com.yandex.runtime.ui_view.ViewProvider

class MapPinsDrawerImpl(
    val mapView: MapView,
    val pinView: View,
    val context: Context
) : IMapPinsDrawer {

    private val friendsGeoMap = HashMap<Int, PlacemarkMapObject>()
    private var placemarkUser: PlacemarkMapObject? = null

    override fun friendsReload(data: List<FriendPinModel>) {
        if (!isTheSameFriends(data)) {
            clearAllFriends()
        }
        data.forEach {
            bindFriend(it)
        }
    }

    override fun userReload(data: UserGeoModel, avatarUrl: String) {
        bindUser(data, avatarUrl)
    }

    override fun moveCameraToUser() {
        placemarkUser?.let { user ->
            mapView.mapWindow.map.move(
                CameraPosition(
                    Point(user.geometry.latitude, user.geometry.longitude),
                    17.0f,
                    0.0f,
                    0.0f
                )
            )
        }
    }

    private fun bindFriend(data: FriendPinModel) {
        val oldFriendGeo = friendsGeoMap[data.userId]
        if (oldFriendGeo != null) {
            if (!areFriendContentsTheSame(oldFriendGeo.userData as FriendPinModel, data)) {
                oldFriendGeo.isVisible = false
                changePlacemarkFriend(data)
                oldFriendGeo.setVisible(true, Animation(Animation.Type.SMOOTH, 0.5f), null)
            }
        } else {
            val newPlacemarkFriend = getNewPlacemarkFriend(data)
            newPlacemarkFriend.setVisible(true, Animation(Animation.Type.SMOOTH, 0.5f), null)
            friendsGeoMap[data.userId] = newPlacemarkFriend
        }
    }

    private fun bindUser(data: UserGeoModel, avatarUrl: String) {
        placemarkUser?.let { existingUser ->
            if (!areUserContentsTheSame(existingUser, data)) {
                existingUser.isVisible = false
                changePlacemarkUser(data, avatarUrl)
                existingUser.setVisible(true, Animation(Animation.Type.SMOOTH, 0.5f), null)
            }
        } ?: run {
            val newPlacemarkUser = getNewPlacemarkUser(data, avatarUrl)
            newPlacemarkUser.setVisible(true, Animation(Animation.Type.SMOOTH, 0.5f), null)
            placemarkUser = newPlacemarkUser
            moveCameraToUser()
        }
    }

    private fun areFriendContentsTheSame(
        oldItem: FriendPinModel,
        newItem: FriendPinModel
    ): Boolean {
        return oldItem == newItem
    }

    private fun areUserContentsTheSame(
        oldItem: PlacemarkMapObject,
        newItem: UserGeoModel
    ): Boolean {
        return (oldItem.geometry.latitude == newItem.latitude && oldItem.geometry.longitude == newItem.longitude)
    }

    private fun getNewPlacemarkFriend(data: FriendPinModel): PlacemarkMapObject {
        val point = Point(data.latitude, data.longitude)
        val newPinView = CustomMapPinView(_context = context)
        val result = newPinView.viewModel.getAvatar(data.avatarUrl)
        newPinView.setProfileImageFromByteArray(result.imageFile)
        val newPlacemarkFriend = mapView.mapWindow.map.mapObjects.addPlacemark().apply {
            geometry = point
            userData = data
            setView(ViewProvider(newPinView), IconStyle().apply {
                anchor = PointF(0.5f, 1.0f)
                scale = 0.9f
            })
            setText(
                data.nickname,
                TextStyle().apply {
                    size = 12f
                    placement = TextStyle.Placement.TOP
                    offset = 5f
                },
            )
            isVisible = false
        }
        return newPlacemarkFriend
    }

    private fun changePlacemarkFriend(data: FriendPinModel) {
        val point = Point(data.latitude, data.longitude)
        val newPinView = CustomMapPinView(_context = context)
        val result = newPinView.viewModel.getAvatar(data.avatarUrl)
        newPinView.setProfileImageFromByteArray(result.imageFile)
        friendsGeoMap[data.userId]?.apply {
            geometry = point
            userData = data
            setView(ViewProvider(newPinView), IconStyle().apply {
                anchor = PointF(0.5f, 1.0f)
                scale = 0.9f
            })
            setText(
                data.nickname,
                TextStyle().apply {
                    size = 12f
                    placement = TextStyle.Placement.TOP
                    offset = 5f
                },
            )
        }
    }

    private fun getNewPlacemarkUser(data: UserGeoModel, avatarUrl: String): PlacemarkMapObject {
        val point = Point(data.latitude, data.longitude)
        val newPinView = CustomMapPinView(_context = context)
        newPinView.setProfileImageFromUrl(avatarUrl)
        val newPlacemarkUser = mapView.mapWindow.map.mapObjects.addPlacemark().apply {
            geometry = point
            setView(ViewProvider(newPinView), IconStyle().apply {
                anchor = PointF(0.5f, 1.0f)
                scale = 0.9f
            })
            setText(
                "You",
                TextStyle().apply {
                    size = 12f
                    placement = TextStyle.Placement.TOP
                    offset = 5f
                },
            )
        }
        return newPlacemarkUser
    }

    private fun changePlacemarkUser(data: UserGeoModel, avatarUrl: String) {
        val point = Point(data.latitude, data.longitude)
        val newPinView = CustomMapPinView(_context = context)
        newPinView.setProfileImageFromUrl(avatarUrl)
        placemarkUser?.apply {
            geometry = point
            setView(ViewProvider(newPinView), IconStyle().apply {
                anchor = PointF(0.5f, 1.0f)
                scale = 0.9f
            })
            setText(
                "You",
                TextStyle().apply {
                    size = 12f
                    placement = TextStyle.Placement.TOP
                    offset = 5f
                },
            )
        }
    }

    private fun isTheSameFriends(data: List<FriendPinModel>): Boolean {
        if (friendsGeoMap.size != data.size) return false

        return data.all { friend ->
            friendsGeoMap[friend.userId] != null
        }
    }

    private fun clearAllFriends() {
        friendsGeoMap.values.forEach {
            mapView.mapWindow.map.mapObjects.remove(it)
        }
        friendsGeoMap.clear()
    }

}
