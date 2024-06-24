package com.surf2024.geobuddies.presentation.views

import android.Manifest
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.surf2024.geobuddies.R
import com.surf2024.geobuddies.databinding.FragmentMapBinding
import com.surf2024.geobuddies.domain.login.entity.UserInfoModel
import com.surf2024.geobuddies.domain.map.utility.IMapPinsDrawer
import com.surf2024.geobuddies.domain.map.utility.IFriendsPinsGenerator
import com.surf2024.geobuddies.domain.map.utility.IMapMenuAnimationHelper
import com.surf2024.geobuddies.domain.map.utilityimpl.FriendsPinsGeneratorImpl
import com.surf2024.geobuddies.domain.map.utilityimpl.MapMenuAnimationHelperImpl
import com.surf2024.geobuddies.data.map.utilityImpl.MapPinsDrawerImpl
import com.surf2024.geobuddies.presentation.viewmodels.map.MapLocationViewModel
import com.surf2024.geobuddies.presentation.viewmodels.map.MapInfoViewModel
import com.yandex.mapkit.MapKitFactory
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
            updateLocations()
            showPermissionGrantedMessage()
        } else {
            showPermissionDeniedMessage()
        }
    }

    private val binding by viewBinding(FragmentMapBinding::class.java)

    private val friendsPinsGenerator: IFriendsPinsGenerator = FriendsPinsGeneratorImpl()

    private lateinit var mapInfoViewModel: MapInfoViewModel
    private lateinit var mapLocationViewModel: MapLocationViewModel

    private lateinit var animationHelper: IMapMenuAnimationHelper

    private lateinit var mapView: MapView
    private lateinit var pinsDrawer: IMapPinsDrawer

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

        initMapInfoViewModel()
        initMapLocationViewModel()

        initMapInfoViewModelObservers()
        initMapLocationViewModelObservers()

        initMapMenuAnimationHelper()

        initMap()
        initMapPinsDrawer()

        initListeners()

        getUserInfo()

        checkLocationPermission()

        getFriends()
        updateLocations()
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
    //////////////////// init /////////////////////////////////////////////////////////////////////
    private fun initMapInfoViewModel() {
        Log.d("Hilt", "Creating MapViewModel client instance")
        mapInfoViewModel = ViewModelProvider(this)[MapInfoViewModel::class.java]
    }
    private fun initMapLocationViewModel() {
        Log.d("Hilt", "Creating LocationViewModel client instance")
        mapLocationViewModel = ViewModelProvider(this)[MapLocationViewModel::class.java]
    }
    private fun initMap(){
        mapView = binding.idMapview
        mapView.mapWindow.map.mapType = MapType.MAP
        MapKitFactory.initialize(requireContext())
    }
    private fun initMapPinsDrawer(){
        pinsDrawer = MapPinsDrawerImpl(mapView, binding.customMapPinView)
    }
    private fun initMapMenuAnimationHelper(){
        animationHelper = MapMenuAnimationHelperImpl(requireContext(), binding)
    }
    private fun initListeners(){
        binding.btnToggleMenu.setOnClickListener {
            animateMapMenu()
        }
        binding.sideMenu.setOnClickListener {
            animateMapMenu()
            updateLocations()
        }
        binding.idMenuPart1.setOnClickListener {}
        binding.idMenuPart2.setOnClickListener {}
    }
    //////////////////// observers /////////////////////////////////////////////////////////////////
    private fun initMapInfoViewModelObservers(){
        mapInfoViewModel.user.observe(viewLifecycleOwner){ user->
            if (user != null){
                setUserInfo(user)
            }
        }
        mapInfoViewModel.friendList.observe(viewLifecycleOwner){ result->
            if(result!=null){
                updateFriendsGeo()
            }
            else{
                showError()
            }
        }
    }
    private fun initMapLocationViewModelObservers() {
        mapLocationViewModel.currentUserGeo.observe(viewLifecycleOwner){ userGeoModel ->
            saveUserGeo(userGeoModel.latitude, userGeoModel.longitude)
        }
        mapLocationViewModel.isSavedUserGeo.observe(viewLifecycleOwner){ result->
            if(result){
                mapLocationViewModel.currentUserGeo.value?.let { pinsDrawer.userReload(it) }
            }
            else{
                showError()
            }
        }
        mapLocationViewModel.friendsGeoList.observe(viewLifecycleOwner){ result->
            if(result!=null){
                generateFriendsPins()
            }
            else{
                showError()
            }
        }
        mapLocationViewModel.getUserGeoFailure.observe(viewLifecycleOwner){
            showGetLocationFailedMessage()
        }
        mapLocationViewModel.permissionsFailure.observe(viewLifecycleOwner){}
        mapLocationViewModel.alertDialogForLocationPermissionsNeeded.observe(viewLifecycleOwner){
            showPermissionExplanation()
        }
        mapLocationViewModel.requestLocationPermissionsNeeded.observe(viewLifecycleOwner){
            requestLocationPermission()
        }
    }
    //////////////////// permission ////////////////////////////////////////////////////////////////
    private fun checkLocationPermission(){
        mapLocationViewModel.checkLocationPermission(this)
    }
    private fun requestLocationPermission() {
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }
    //////////////////// location //////////////////////////////////////////////////////////////////
    private fun updateLocations(){
        updateCurrentUserLocation()
        updateFriendsGeo()
    }
    private fun updateCurrentUserLocation() {
        mapLocationViewModel.getCurrentUserLocation()
    }
    private fun updateFriendsGeo(){
        mapLocationViewModel.getFriendsGeo()
    }
    private fun saveUserGeo(latitude: Double, longitude: Double){
        mapLocationViewModel.saveUserGeo(latitude = latitude, longitude = longitude)
    }
    //////////////////// additional info ///////////////////////////////////////////////////////////
    private fun getFriends(){
        mapInfoViewModel.getFriends()
    }
    private fun getUserInfo(){
        mapInfoViewModel.getUserInfo()
    }
    //////////////////// pins //////////////////////////////////////////////////////////////////////
    private fun generateFriendsPins(){
        if (mapInfoViewModel.friendList.value != null &&
            mapLocationViewModel.friendsGeoList.value != null){

            friendsPinsGenerator.generateFriendsPins(
                friendList = mapInfoViewModel.friendList.value!!,
                friendsGeoList = mapLocationViewModel.friendsGeoList.value!!,

                { friendsPinsList ->
                    pinsDrawer.friendsReload(friendsPinsList)
                },{
                    getFriends()
                },{
                    showError()
                }
            )
        }
    }
    //////////////////// animations ////////////////////////////////////////////////////////////////
    private fun animateMapMenu() {
        animationHelper.animateMapMenu()
    }
    //////////////////// other IU fun //////////////////////////////////////////////////////////////
    private fun setUserInfo(user: UserInfoModel){
        binding.menuUserName.text = user.name
        binding.menuUserEmail.text = user.email
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
}