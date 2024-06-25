
package com.surf2024.geobuddies.data.map.utilityImpl

import android.graphics.PointF
import android.view.View
import com.surf2024.geobuddies.domain.map.entity.FriendPinModel
import com.surf2024.geobuddies.domain.map.entity.UserGeoModel
import com.surf2024.geobuddies.domain.map.utility.IMapPinsDrawer
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.MapType
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.mapkit.map.TextStyle
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.ui_view.ViewProvider
class MapPinsDrawerImpl(
    val mapView: MapView,
    val pinView: View,
): IMapPinsDrawer {
    private val friendsGeoMap = HashMap<Int, Pair<FriendPinModel, PlacemarkMapObject>>()
    private var placemarkUser: PlacemarkMapObject? = null

    override fun friendsReload(data: List<FriendPinModel>){
        if ( !isTheSameFriends(data) ){
            clearAllFriends()
        }
        data.forEach{
            bindFriend(it)
        }
    }

    override fun userReload(data: UserGeoModel){
        bindUser(data)
    }

    override fun moveCameraToUser(){
        if (placemarkUser != null){
            mapView.mapWindow.map.move(
                CameraPosition(
                    Point(placemarkUser!!.geometry.latitude, placemarkUser!!.geometry.longitude),
                    17.0f,
                    0.0f,
                    0.0f)
            )
        }
    }

    private fun bindFriend(data: FriendPinModel){
        val oldFriendGeo = friendsGeoMap[data.userId]
        if (oldFriendGeo != null){
            if( !areFriendContentsTheSame(oldFriendGeo.first, data) ){
                mapView.mapWindow.map.mapObjects.remove(oldFriendGeo.second)

                val newPlacemarkFriend = getNewPlacemarkFriend(data)
                friendsGeoMap[data.userId] = Pair(data, newPlacemarkFriend)
            }
        } else{
            val newPlacemarkFriend = getNewPlacemarkFriend(data)
            friendsGeoMap[data.userId] = Pair(data, newPlacemarkFriend)
        }
    }

    private fun bindUser(data: UserGeoModel){
        if(placemarkUser != null){
            if(!areUserContentsTheSame(placemarkUser!!, data)) {
                mapView.mapWindow.map.mapObjects.remove(placemarkUser!!)
                val newPlacemarkUser = getNewPlacemarkUser(data)
                placemarkUser = newPlacemarkUser
            }
        }
        else{
            val newPlacemarkUser = getNewPlacemarkUser(data)
            placemarkUser = newPlacemarkUser
            moveCameraToUser()
        }
    }
    private fun areFriendContentsTheSame(oldItem: FriendPinModel, newItem: FriendPinModel): Boolean {
        return oldItem == newItem
    }
    private fun areUserContentsTheSame(oldItem: PlacemarkMapObject, newItem: UserGeoModel): Boolean {
        return (oldItem.geometry.latitude == newItem.latitude &&
                oldItem.geometry.longitude == newItem.longitude)
    }
    private fun getNewPlacemarkFriend(data: FriendPinModel): PlacemarkMapObject{
        val point = Point(data.latitude, data.longitude)
        val newPlacemarkFriend = mapView.mapWindow.map.mapObjects.addPlacemark().apply {
            geometry = point
            userData = data.userId
            setView(
                ViewProvider(pinView),
                IconStyle().apply {
                    anchor = PointF(0.5f, 1.0f)
                    scale = 0.9f
                }
            )
            setText(
                data.nickname,
                TextStyle().apply {
                    size = 12f
                    placement = TextStyle.Placement.TOP
                    offset = 5f
                },
            )
        }
        return newPlacemarkFriend
    }
    private fun getNewPlacemarkUser(data: UserGeoModel): PlacemarkMapObject{
        val point = Point(data.latitude, data.longitude)
        val newPlacemarkUser = mapView.mapWindow.map.mapObjects.addPlacemark().apply {
            geometry = point
            setView(
                ViewProvider(pinView),
                IconStyle().apply {
                    anchor = PointF(0.5f, 1.0f)
                    scale = 0.9f
                }
            )
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
    private fun isTheSameFriends(data: List<FriendPinModel>): Boolean {
        if (friendsGeoMap.size != data.size) return false

        return data.all { friend ->
            friendsGeoMap[friend.userId] != null
        }
    }
    private fun clearAllFriends() {
        friendsGeoMap.values.forEach {
            mapView.mapWindow.map.mapObjects.remove(it.second)
        }
        friendsGeoMap.clear()
    }
}
