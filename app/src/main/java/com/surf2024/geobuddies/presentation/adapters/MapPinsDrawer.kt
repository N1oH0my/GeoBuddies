
package com.surf2024.geobuddies.presentation.adapters

import android.graphics.PointF
import android.view.View
import com.surf2024.geobuddies.domain.map.entity.FriendGeoModel
import com.surf2024.geobuddies.domain.map.entity.UserGeoModel
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.mapkit.map.TextStyle
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.ui_view.ViewProvider
class MapPinsDrawer(
    val mapView: MapView,
    val pinView: View,
) {
    private val placemarkList: MutableList<PlacemarkMapObject?> = mutableListOf()
    private var placemarkUser: PlacemarkMapObject? = null

    fun friendsReload(data: List<FriendGeoModel>){
        for (i in 0 until data.size){
            bind(data[i], i)
        }
    }
    fun userReload(data: UserGeoModel){
        bindUser(data)
    }
    private fun bind(data: FriendGeoModel, position: Int){
        val placemarkFriend: PlacemarkMapObject? = placemarkList.getOrNull(position)

        if (placemarkFriend != null) {
            mapView.mapWindow.map.mapObjects.remove(placemarkFriend!!)
        }

        val point = Point(data.latitude, data.longitude)

        val newPlacemarkFriend = mapView.mapWindow.map.mapObjects.addPlacemark().apply {
            geometry = point
            setView(
                ViewProvider(pinView),
                IconStyle().apply {
                    anchor = PointF(0.5f, 1.0f)
                    scale = 0.9f
                }
            )
            setText(
                "Friend #${data.userId}",
                TextStyle().apply {
                    size = 12f
                    placement = TextStyle.Placement.TOP
                    offset = 5f
                },
            )
        }
        if (position < placemarkList.size){
            placemarkList[position] = newPlacemarkFriend
        } else{
            placemarkList.add(newPlacemarkFriend)
        }
    }
    private fun bindUser(data: UserGeoModel){
        if(placemarkUser!=null){
            mapView.mapWindow.map.mapObjects.remove(placemarkUser!!)
        }
        val point = Point(data.latitude, data.longitude)
        mapView.mapWindow.map.move(
            CameraPosition(
            point,
            17.0f,
            0.0f,
            0.0f)
        )
        placemarkUser = mapView.mapWindow.map.mapObjects.addPlacemark().apply {
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
    }
}
