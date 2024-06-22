package com.surf2024.geobuddies.presentation.views

import android.Manifest
import android.animation.Animator
import android.animation.ValueAnimator
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.surf2024.geobuddies.R
import com.surf2024.geobuddies.databinding.FragmentMapBinding
import com.surf2024.geobuddies.domain.map.entity.UserGeoModel
import com.surf2024.geobuddies.domain.map.repository.ILocationRepository
import com.surf2024.geobuddies.domain.map.repository.IMapPinsDrawer
import com.surf2024.geobuddies.domain.map.repositoryimpl.LocationRepositoryImpl
import com.surf2024.geobuddies.domain.map.utility.IMapMenuAnimationHelper
import com.surf2024.geobuddies.domain.map.utilityimpl.LocationPermissionChecker
import com.surf2024.geobuddies.domain.map.utilityimpl.MapMenuAnimationHelper
import com.surf2024.geobuddies.presentation.adapters.MapPinsDrawer
import com.surf2024.geobuddies.presentation.viewmodels.LocationViewModel
import com.surf2024.geobuddies.presentation.viewmodels.MapViewModel
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapType
import com.yandex.mapkit.mapview.MapView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapFragment : Fragment() {
    companion object {
        @JvmStatic
        fun newInstance() =
            MapFragment().apply {
                arguments = Bundle().apply {}
            }
        }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            getLocation()
            showPermissionGrantedMessage()
        } else {
            showPermissionDeniedMessage()
        }
    }

    private lateinit var animationHelper: IMapMenuAnimationHelper

    private lateinit var mapView: MapView
    private lateinit var pinsDrawer: IMapPinsDrawer


    private val binding by viewBinding(FragmentMapBinding::class.java)
    private lateinit var mapViewModel: MapViewModel
    private lateinit var locationViewModel: LocationViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMapViewModel()
        initLocationViewModel()

        initObserversMapViewModel()
        initLocationViewModelObservers()

        initMapMenuAnimationHelper()

        initMap()
        initMapPinsDrawer()

        initListeners()

        checkLocationPermission()

        getLocation()
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }

    override fun onStop() {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    private fun initMapViewModel() {
        Log.d("Hilt", "Creating MapViewModel client instance")
        mapViewModel = ViewModelProvider(this)[MapViewModel::class.java]
    }
    private fun initLocationViewModel() {
        Log.d("Hilt", "Creating LocationViewModel client instance")
        locationViewModel = ViewModelProvider(this)[LocationViewModel::class.java]
    }
    private fun initMap(){
        mapView = binding.idMapview
        mapView.mapWindow.map.mapType = MapType.MAP
        MapKitFactory.initialize(requireContext())
    }
    private fun initMapPinsDrawer(){
        pinsDrawer = MapPinsDrawer(mapView, binding.customMapPinView)
    }
    private fun initMapMenuAnimationHelper(){
        animationHelper = MapMenuAnimationHelper(requireContext(), binding)
    }
    private fun initLocationViewModelObservers(){
        locationViewModel.currentUserGeo.observe(viewLifecycleOwner){ userGeoModel ->
            updateUserGeo(userGeoModel.latitude, userGeoModel.longitude)
            updateFriendsGeo()
        }
        locationViewModel.permissionsFailure.observe(viewLifecycleOwner){
            updateFriendsGeo()
        }
        locationViewModel.getUserGeoFailure.observe(viewLifecycleOwner){
            showGetLocationFailedMessage()
            updateFriendsGeo()
        }
        locationViewModel.alertDialogForLocationPermissionsNeeded.observe(viewLifecycleOwner){
            showPermissionExplanation()
        }
        locationViewModel.requestLocationPermissionsNeeded.observe(viewLifecycleOwner){
            requestLocationPermission()
        }
    }
    private fun initObserversMapViewModel() {

        mapViewModel.friendsGeoList.observe(viewLifecycleOwner){ friendsGeoList->
            if(friendsGeoList != null){
                pinsDrawer.friendsReload(friendsGeoList)
            }
            else{
                showError()
            }
        }
        mapViewModel.userGeo.observe(viewLifecycleOwner){ result->
            if(result){
                locationViewModel.currentUserGeo.value?.let { pinsDrawer.userReload(it) }
            }
            else{
                showError()
            }
        }
    }
    private fun initListeners(){
        binding.btnToggleMenu.setOnClickListener {
            animateMapMenu()
        }
        binding.sideMenu.setOnClickListener {
            animateMapMenu()
            getLocation()
        }
        binding.idMenuPart1.setOnClickListener {}
        binding.idMenuPart2.setOnClickListener {}
    }

    private fun checkLocationPermission(){
        locationViewModel.checkLocationPermission(this)
    }
    private fun requestLocationPermission() {
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }
    private fun getLocation() {
        locationViewModel.getLocation()
    }

    private fun updateUserGeo(latitude: Double, longitude: Double){
        mapViewModel.saveUserGeo(latitude = latitude, longitude = longitude)
    }
    private fun updateFriendsGeo(){
        mapViewModel.getFriendsGeo()
    }

    private fun animateMapMenu() {
        animationHelper.animateMapMenu()
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
    private fun showError(){
        activity?.let {
            showToast(it.getString(R.string.error_part_1))
            showToast(it.getString(R.string.error_part_2))
        }
    }
    private fun showPermissionExplanation() {
        activity?.let {
            AlertDialog.Builder(requireContext())
                .setTitle(it.getString(R.string.title_why_location_permission_needed))
                .setMessage(
                    it.getString(R.string.msg_why_location_permission_needed)
                )
                .setPositiveButton(it.getString(R.string.ok)) { _, _ ->
                    requestLocationPermission()
                }
                .setNegativeButton(it.getString(R.string.cancel)) { dialog, _ ->
                    dialog.dismiss()
                    showPermissionDeniedMessage()
                }
                .create()
                .show()
        }
    }

    private fun showPermissionDeniedMessage() {
        activity?.let {
            showToast(it.getString(R.string.location_permission_denied))
        }
    }
    private fun showPermissionGrantedMessage() {
        activity?.let {
            showToast(it.getString(R.string.location_permission_granted))
        }
    }
    private fun showGetLocationFailedMessage() {
        activity?.let {
            showToast(it.getString(R.string.failed_to_get_location))
        }
    }
}