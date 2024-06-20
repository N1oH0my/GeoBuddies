package com.surf2024.geobuddies.presentation.views

import android.animation.Animator
import android.animation.ValueAnimator
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.surf2024.geobuddies.R
import com.surf2024.geobuddies.databinding.FragmentMapBinding
import com.surf2024.geobuddies.domain.map.entity.UserGeoModel
import com.surf2024.geobuddies.presentation.adapters.MapPinsDrawer
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
        private val POINT1 = Point(59.751280, 37.629720)
        private val POINT2 = Point(37.751590, 37.630030)
        private val POSITION = CameraPosition(POINT1, 17.0f, 0.0f, 0.0f)
        private var currentUserGeo = UserGeoModel(0.0, 0.0)
    }

    private lateinit var mapShadowAnimation: ValueAnimator
    private lateinit var slideInAnimator: ValueAnimator
    private lateinit var slideOutAnimator: ValueAnimator
    private lateinit var fadeInAnimator: ValueAnimator
    private lateinit var fadeOutAnimator: ValueAnimator

    private lateinit var mapView: MapView

    private val binding by viewBinding(FragmentMapBinding::class.java)
    private lateinit var mapViewModel: MapViewModel

    private lateinit var pinsDrawer: MapPinsDrawer

    private var isMenuOpen = false

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
        initObserversMapViewModel()

        initAnimation()

        initMap()
        initMapPinsDrawer()

        initListeners()

        saveUserGeo(POINT1.longitude, POINT1.latitude)
        updateGeo()

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
                pinsDrawer.userReload(currentUserGeo)
            }
            else{
                showError()
            }
        }
    }
    private fun initMapPinsDrawer(){
        pinsDrawer = MapPinsDrawer(mapView, binding.customMapPinView)
    }
    private fun initListeners(){
        binding.btnToggleMenu.setOnClickListener {
            toggleMenu()
        }
        binding.sideMenu.setOnClickListener {
            toggleMenu()
            saveUserGeo(POINT2.longitude, POINT2.latitude)
            updateGeo()
        }
        binding.idMenuPart1.setOnClickListener {}
        binding.idMenuPart2.setOnClickListener {}
    }

    private fun initAnimation(){
        val initialColor = 0x00000000
        val finalColor = ContextCompat.getColor(requireContext(), R.color.menu_map_shadow)

        mapShadowAnimation = ValueAnimator.ofArgb(initialColor, finalColor).apply {
            duration = 600
            addUpdateListener { animator ->
                val animatedValue = animator.animatedValue as Int
                binding.sideMenu.setBackgroundColor(animatedValue)
            }
        }

        slideInAnimator = ValueAnimator.ofFloat(-1f, 0f).apply {
            duration = 400
            addUpdateListener { animator ->
                val slideOffset = animator.animatedValue as Float
                binding.idMenuPart1.translationX = slideOffset * binding.idMenuPart1.width
                binding.idMenuPart2.translationX = slideOffset * binding.idMenuPart2.width
            }
        }
        slideInAnimator.doOnStart {
            menuVisible()
        }
        slideOutAnimator = ValueAnimator.ofFloat(0f, -1f).apply {
            duration = 400
            addUpdateListener { animator ->
                val slideOffset = animator.animatedValue as Float
                binding.idMenuPart1.translationX = slideOffset * binding.idMenuPart1.width
                binding.idMenuPart2.translationX = slideOffset * binding.idMenuPart2.width
            }
        }

        slideOutAnimator.doOnEnd {
            menuGone()
        }

        fadeInAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 500
            addUpdateListener { animator ->
                binding.sideMenu.alpha = animator.animatedValue as Float
            }
        }

        fadeOutAnimator = ValueAnimator.ofFloat(1f, 0f).apply {
            duration = 500
            addUpdateListener { animator ->
                binding.sideMenu.alpha = animator.animatedValue as Float
            }
        }

        fadeOutAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {
                binding.sideMenu.visibility = View.GONE
            }
            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}

        })

    }
    private fun initMap(){
        mapView = binding.idMapview
        mapView.mapWindow.map.mapType = MapType.MAP
        MapKitFactory.initialize(requireContext())

    }

    private fun updateGeo(){
        mapViewModel.clearRequests()
        mapViewModel.getFriendsGeo()
        mapViewModel.saveUserGeo(currentUserGeo.longitude, currentUserGeo.latitude)
    }

    private fun saveUserGeo(longitude: Double, latitude: Double){
        // test data
        //currentUserGeo = UserGeoModel(longitude, latitude)
        //pinsDrawer.userReload(currentUserGeo)
        currentUserGeo = UserGeoModel(longitude, latitude)
    }

    private fun toggleMenu() {
        startMapShadowAnimation()
        if (!isMenuOpen) {
            binding.idMenuPart1.translationX = -binding.idMenuPart1.width.toFloat()
            binding.idMenuPart2.translationX = -binding.idMenuPart2.width.toFloat()
            binding.sideMenu.alpha = 0f

            slideInAnimator.start()
            fadeInAnimator.start()
        } else {
            slideOutAnimator.start()
            fadeOutAnimator.start()
        }
        isMenuOpen = !isMenuOpen
    }
    private fun startMapShadowAnimation() {
        if (::mapShadowAnimation.isInitialized) {
            if (!isMenuOpen){
                mapShadowAnimation.start()
            } else{
                mapShadowAnimation.reverse()
            }
        }
    }
    private fun menuGone(){
        binding.idMenuPart1.visibility = View.GONE
        binding.idMenuPart2.visibility = View.GONE
        binding.sideMenu.visibility = View.GONE
        binding.btnToggleMenu.visibility = View.VISIBLE
    }
    private fun menuVisible(){
        binding.idMenuPart1.visibility = View.VISIBLE
        binding.idMenuPart2.visibility = View.VISIBLE
        binding.sideMenu.visibility = View.VISIBLE
        binding.btnToggleMenu.visibility = View.GONE
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
}