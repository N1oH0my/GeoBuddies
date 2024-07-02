package com.surf2024.geobuddies.presentation.views

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.surf2024.geobuddies.R
import com.surf2024.geobuddies.data.map.utilityImpl.MapPinsDrawerImpl
import com.surf2024.geobuddies.databinding.FragmentMapBinding
import com.surf2024.geobuddies.domain.common.utility.IButtonAnimationHelper
import com.surf2024.geobuddies.domain.common.utilityimpl.ButtonAnimationHelperImpl
import com.surf2024.geobuddies.domain.login.entity.UserInfoModel
import com.surf2024.geobuddies.domain.main.usecase.FragmentChangeListener
import com.surf2024.geobuddies.domain.map.utility.IFriendsPinsGenerator
import com.surf2024.geobuddies.domain.map.utility.IMapMenuAnimationHelper
import com.surf2024.geobuddies.domain.map.utility.IMapPinsDrawer
import com.surf2024.geobuddies.domain.map.utilityimpl.MapMenuAnimationHelperImpl
import com.surf2024.geobuddies.presentation.viewmodels.map.MapInfoViewModel
import com.surf2024.geobuddies.presentation.viewmodels.map.MapLocationViewModel
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.map.MapType
import com.yandex.mapkit.mapview.MapView
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class MapFragment : Fragment() {

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            showPermissionGrantedMessage()
        } else {
            showPermissionDeniedMessage()
        }
    }

    @Inject
    lateinit var buttonAnimationHelper: IButtonAnimationHelper
    @Inject
    lateinit var friendsPinsGenerator: IFriendsPinsGenerator

    private val binding by viewBinding(FragmentMapBinding::class.java)

    private var scheduler = Executors.newScheduledThreadPool(1)
    private var scheduledFuture: ScheduledFuture<*>? = null

    private lateinit var fragmentsTapListener: FragmentChangeListener

    private lateinit var mapInfoViewModel: MapInfoViewModel
    private lateinit var mapLocationViewModel: MapLocationViewModel

    private lateinit var animationHelper: IMapMenuAnimationHelper

    private lateinit var mapView: MapView
    private lateinit var pinsDrawer: IMapPinsDrawer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        overrideOnBackPressed()

        initMapInfoViewModel()
        initMapLocationViewModel()

        initMapInfoViewModelObservers()
        initMapLocationViewModelObservers()

        initMapMenuAnimationHelper()

        initMap()
        initMapPinsDrawer()

        setButtonTouchAnimation()
        initListeners()

        getUserInfo()

        checkLocationPermission()

        getFriends()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentsTapListener = context as FragmentChangeListener
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        startScheduler()
    }

    override fun onPause() {
        super.onPause()
        pauseScheduler()
    }

    override fun onStop() {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onDestroy() {
        stopScheduler()
        super.onDestroy()
    }

    private fun overrideOnBackPressed() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                closeMap()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
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

    private fun initMap() {
        mapView = binding.idMapview
        mapView.mapWindow.map.mapType = MapType.MAP
        MapKitFactory.initialize(requireContext())
    }

    private fun initMapPinsDrawer() {
        pinsDrawer = MapPinsDrawerImpl(mapView, binding.customMapPinView)
    }

    private fun initMapMenuAnimationHelper() {
        animationHelper = MapMenuAnimationHelperImpl(requireContext(), binding)
    }

    private fun setButtonTouchAnimation() {
        with(buttonAnimationHelper) {
            setTouchAnimation(binding.btnFindUserPin)
            setTouchAnimation(binding.btnToggleMenu)
            setTouchAnimation(binding.menuFriendsImageview)
            setTouchAnimation(binding.menuAddFriendsImageview)
            setTouchAnimation(binding.menuInvitesImageview)
            setTouchAnimation(binding.menuLogoutImageview)
        }
    }

    private fun initListeners() {
        binding.btnFindUserPin.setOnClickListener {
            pinsDrawer.moveCameraToUser()
        }
        binding.btnToggleMenu.setOnClickListener {
            animateMapMenu()
        }
        binding.sideMenu.setOnClickListener {
            animateMapMenu()
        }
        binding.menuMyFriends.setOnClickListener {
            openFriends()
        }
        binding.menuFriendsImageview.setOnClickListener {
            openFriends()
        }
        binding.menuAddFriends.setOnClickListener {
            openSearchFriends()
        }
        binding.menuAddFriendsImageview.setOnClickListener {
            openSearchFriends()
        }
        binding.menuInvites.setOnClickListener {
            openInvites()
        }
        binding.menuInvitesImageview.setOnClickListener {
            openInvites()
        }
        binding.menuLogout.setOnClickListener {
            resetToken()
        }
        binding.menuLogoutImageview.setOnClickListener {
            resetToken()
        }
        binding.idMenuPart1.setOnClickListener {}
        binding.idMenuPart2.setOnClickListener {}
    }

    //////////////////// observers /////////////////////////////////////////////////////////////////
    private fun initMapInfoViewModelObservers() {
        mapInfoViewModel.user.observe(viewLifecycleOwner) { user ->
            setUserInfo(user)
        }
        mapInfoViewModel.friendList.observe(viewLifecycleOwner) {
            updateFriendsGeo()
        }
        mapInfoViewModel.serverError.observe(viewLifecycleOwner) {
            showError()
        }
        mapInfoViewModel.tokenReset.observe(viewLifecycleOwner) {
            logOut()
        }
    }

    private fun initMapLocationViewModelObservers() {
        mapLocationViewModel.currentUserGeo.observe(viewLifecycleOwner) { userGeoModel ->
            saveUserGeo(userGeoModel.latitude, userGeoModel.longitude)
        }
        mapLocationViewModel.userGeoSaved.observe(viewLifecycleOwner) {
            mapLocationViewModel.currentUserGeo.value?.let { pinsDrawer.userReload(it) }
        }
        mapLocationViewModel.friendsGeoList.observe(viewLifecycleOwner) {
            generateFriendsPins()
        }
        mapLocationViewModel.serverError.observe(viewLifecycleOwner) {
            showError()
        }
        mapLocationViewModel.getUserGeoFailure.observe(viewLifecycleOwner) {
            showGetLocationFailedMessage()
        }
        mapLocationViewModel.alertDialogForLocationPermissionsNeeded.observe(viewLifecycleOwner) {
            showPermissionExplanation()
        }
        mapLocationViewModel.requestLocationPermissionsNeeded.observe(viewLifecycleOwner) {
            requestLocationPermission()
        }
        mapLocationViewModel.permissionsFailure.observe(viewLifecycleOwner) {}
    }

    //////////////////// task scheduler ////////////////////////////////////////////////////////////
    private fun startScheduler() {
        if (scheduler == null || scheduler?.isShutdown == true) {
            scheduler = Executors.newScheduledThreadPool(1)
        }
        if (scheduledFuture == null || scheduledFuture?.isCancelled == true) {
            scheduledFuture = scheduler.scheduleAtFixedRate({
                try {
                    Log.d("Scheduler", "Updating user and friends' geo")
                    updateLocations()
                } catch (e: Exception) {
                    Log.e("Scheduler", "Error in scheduled task: ${e.message}", e)
                }
            }, 0, 5, TimeUnit.SECONDS)
        } else {
            Log.d("Scheduler", "Scheduler is already running.")
        }
    }

    private fun pauseScheduler() {
        scheduledFuture?.let {
            if (!it.isCancelled && !it.isDone) {
                it.cancel(true)
                Log.d("Scheduler", "Scheduler paused.")
            }
        }
    }

    private fun stopScheduler() {
        scheduler.shutdown()
        try {
            if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                scheduler.shutdownNow()
                if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                    Log.e("Scheduler", "Scheduler did not terminate in time.")
                }
            }
        } catch (ie: InterruptedException) {
            scheduler.shutdownNow()
            Thread.currentThread().interrupt()
        }
        scheduler = null
        Log.d("Scheduler", "Scheduler stopped.")
    }

    //////////////////// permission ////////////////////////////////////////////////////////////////
    private fun checkLocationPermission() {
        mapLocationViewModel.checkLocationPermission(this)
    }

    private fun requestLocationPermission() {
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    //////////////////// location //////////////////////////////////////////////////////////////////
    private fun updateLocations() {
        updateFriendsGeo()
        updateCurrentUserLocation()
    }

    private fun updateCurrentUserLocation() {
        mapLocationViewModel.getCurrentUserLocation()
    }

    private fun updateFriendsGeo() {
        mapLocationViewModel.getFriendsGeo()
    }

    private fun saveUserGeo(latitude: Double, longitude: Double) {
        mapLocationViewModel.saveUserGeo(latitude = latitude, longitude = longitude)
    }

    //////////////////// additional info ///////////////////////////////////////////////////////////
    private fun getFriends() {
        mapInfoViewModel.getFriends()
    }

    private fun getUserInfo() {
        mapInfoViewModel.getUserInfo()
    }

    //////////////////// pins //////////////////////////////////////////////////////////////////////
    private fun generateFriendsPins() {
        mapInfoViewModel.friendList.value?.let { friendList ->
            mapLocationViewModel.friendsGeoList.value?.let { friendsGeoList ->
                friendsPinsGenerator.generateFriendsPins(
                    friendList = friendList,
                    friendsGeoList = friendsGeoList,
                    onSuccess = { friendsPinsList ->
                        pinsDrawer.friendsReload(friendsPinsList)
                    },
                    onDifferentSizesFailure = {
                        getFriends()
                    },
                    onFailure = {
                        showError()
                    }
                )
            }
        }
    }

    //////////////////// animations ////////////////////////////////////////////////////////////////
    private fun animateMapMenu() {
        animationHelper.animateMapMenu()
    }

    //////////////////// fragments change //////////////////////////////////////////////////////////
    private fun closeMap() {
        fragmentsTapListener.onMapClose()
    }

    private fun openInvites() {
        fragmentsTapListener.openInvites()
    }

    private fun openFriends() {
        fragmentsTapListener.openFriends()
    }

    private fun openSearchFriends() {
        fragmentsTapListener.openFriendsSearch()
    }

    private fun logOut() {
        fragmentsTapListener.onLogOut()
    }

    //////////////////// memory ////////////////////////////////////////////////////////////////////
    private fun resetToken() {
        mapInfoViewModel.resetRefreshToken()
    }

    //////////////////// other IU fun //////////////////////////////////////////////////////////////
    private fun setUserInfo(user: UserInfoModel) {
        binding.menuUserName.text = user.name
        binding.menuUserEmail.text = user.email
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showError() {
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
                .setTitle(it.getString(R.string.title_why_location_permission_needed)).setMessage(
                    it.getString(R.string.msg_why_location_permission_needed)
                ).setPositiveButton(it.getString(R.string.ok)) { _, _ ->
                    requestLocationPermission()
                }.setNegativeButton(it.getString(R.string.cancel)) { dialog, _ ->
                    dialog.dismiss()
                    showPermissionDeniedMessage()
                }.create().show()
        }
    }

}