
package com.surf2024.geobuddies.data.map.utilityImpl

import android.graphics.PointF
import android.view.View
import com.surf2024.geobuddies.domain.invites.entities.InviteModel
import com.surf2024.geobuddies.domain.map.entity.FriendPinModel
import com.surf2024.geobuddies.domain.map.entity.UserGeoModel
import com.surf2024.geobuddies.domain.map.utility.IMapPinsDrawer
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.mapkit.map.TextStyle
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.ui_view.ViewProvider
class MapPinsDrawerImpl(
    val mapView: MapView,
    val pinView: View,
): IMapPinsDrawer {
    private val placemarkList: MutableList<PlacemarkMapObject?> = mutableListOf()
    private var placemarkUser: PlacemarkMapObject? = null

    override fun friendsReload(data: List<FriendPinModel>){
        if (placemarkList.size > data.size){
            clearAllFriends()
        }
        for (i in 0 until data.size){
            bindFriend(data[i])
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
                    20.0f,
                    0.0f,
                    0.0f)
            )
        }
    }
    private fun bindFriend(data: FriendPinModel){
        val placemarkFriend: PlacemarkMapObject? = placemarkList.firstOrNull { it?.userData == data.userId }

        if (placemarkFriend != null) {
            if (!areFriendItemsTheSame(placemarkFriend, data) || !areFriendContentsTheSame(placemarkFriend, data))
            {
                mapView.mapWindow.map.mapObjects.remove(placemarkFriend!!)

                val newPlacemarkFriend = getNewPlacemarkFriend(data)

                val position = getPositionById(placemarkFriend.userData as Int)
                if (position != null){
                    placemarkList[position] = newPlacemarkFriend
                }
            }
        } else{
            val newPlacemarkFriend = getNewPlacemarkFriend(data)
            placemarkList.add(newPlacemarkFriend)
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
    private fun areFriendItemsTheSame(oldItem: PlacemarkMapObject, newItem: FriendPinModel): Boolean {
        return oldItem.userData as Int == newItem.userId
    }
    private fun areFriendContentsTheSame(oldItem: PlacemarkMapObject, newItem: FriendPinModel): Boolean {
        return (oldItem.geometry.latitude == newItem.latitude &&
                oldItem.geometry.longitude == newItem.longitude)
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
    private fun getPositionById(id: Int): Int? {
        return placemarkList.indexOfFirst { it?.userData as Int == id }.takeIf { it >= 0 }
    }
    private fun clearAllFriends(){
        for (i in 0 until placemarkList.size){
            placemarkList[i]?.let { mapView.mapWindow.map.mapObjects.remove(it) }
        }
        placemarkList.clear()
    }
}
