package com.surf2024.geobuddies.presentation.adapters

import android.content.Context
import android.graphics.PointF
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.surf2024.geobuddies.R
import com.surf2024.geobuddies.databinding.FragmentMapBinding
import com.surf2024.geobuddies.domain.map.entity.FriendGeoModel
import com.surf2024.geobuddies.domain.map.entity.UserGeoModel
import com.surf2024.geobuddies.presentation.views.MapFragment
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.mapkit.map.TextStyle
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.ui_view.ViewProvider

class MapPinsAdapter(
    private val context: Context,
    val binding: FragmentMapBinding,
    mapView: MapView,
): ListAdapter<FriendGeoModel, MapPinsAdapter.PinViewHolder>(PinDiffCallback()) {

    private var _map: com.yandex.mapkit.map.Map
    init{
        _map = mapView.mapWindow.map
    }

    private val placemarkList: MutableList<PlacemarkMapObject> = mutableListOf()
    private var placemarkUser: PlacemarkMapObject? = null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PinViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_map, parent, false)
        return PinViewHolder(view)
    }

    override fun onBindViewHolder(holder: PinViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }
    override fun getItemCount(): Int {
        return currentList.size+1
    }
    inner class PinViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(data: FriendGeoModel){
            val position = adapterPosition
            val placemarkFriend: PlacemarkMapObject? = placemarkList.getOrNull(position)

            if (placemarkFriend != null) {
                _map.mapObjects.remove(placemarkFriend!!)
            }

            val point = Point(data.latitude.toDouble(), data.longitude.toDouble())

            val newPlacemarkFriend = _map.mapObjects.addPlacemark().apply {
                geometry = point
                setView(
                    ViewProvider(binding.customMapPinView),
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

    }
    class PinDiffCallback : DiffUtil.ItemCallback<FriendGeoModel>() {
        override fun areItemsTheSame(oldItem: FriendGeoModel, newItem: FriendGeoModel): Boolean {
            return oldItem.userId == newItem.userId
        }

        override fun areContentsTheSame(oldItem: FriendGeoModel, newItem: FriendGeoModel): Boolean {
            return oldItem == newItem
        }
    }

    fun friendsReload(data: List<FriendGeoModel>){
        submitList(data)
    }
    fun userReload(data: UserGeoModel){
        bindUser(data)
    }

    private fun bindUser(data: UserGeoModel){
        if(placemarkUser!=null){
            _map.mapObjects.remove(placemarkUser!!)
        }
        val point = Point(data.latitude.toDouble(), data.longitude.toDouble())
        _map.move(CameraPosition(
            point,
            17.0f,
            0.0f,
            0.0f)
        )
        placemarkUser = _map.mapObjects.addPlacemark().apply {
            geometry = point
            setView(
                ViewProvider(binding.customMapPinView),
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