
package com.surf2024.geobuddies.data.map.utilityImpl

import android.graphics.PointF
import android.view.View
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
        for (i in 0 until data.size){
            bindFriend(data[i], i)
        }
    }
    override fun userReload(data: UserGeoModel){
        bindUser(data)
    }
    private fun bindFriend(data: FriendPinModel, position: Int){
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
                data.nickname,
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
        val point = Point(data.latitude, data.longitude)

        if(placemarkUser!=null){
            mapView.mapWindow.map.mapObjects.remove(placemarkUser!!)
        }
        else{
            mapView.mapWindow.map.move(
                CameraPosition(
                point,
                17.0f,
                0.0f,
                0.0f)
            )
        }

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
