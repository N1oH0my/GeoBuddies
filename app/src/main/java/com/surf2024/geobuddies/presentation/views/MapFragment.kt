package com.surf2024.geobuddies.presentation.views

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.content.ContextCompat
import by.kirich1409.viewbindingdelegate.viewBinding
import com.surf2024.geobuddies.R
import com.surf2024.geobuddies.databinding.FragmentMapBinding
import com.surf2024.geobuddies.domain.main.usecase.FragmentChangeListener
import com.yandex.mapkit.MapKitFactory
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
    private lateinit var mapShadowAnimation: ValueAnimator
    private lateinit var slideInAnimator: ValueAnimator
    private lateinit var slideOutAnimator: ValueAnimator
    private lateinit var fadeInAnimator: ValueAnimator
    private lateinit var fadeOutAnimator: ValueAnimator

    private lateinit var mapView: MapView

    private val binding by viewBinding(FragmentMapBinding::class.java)
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
        initAnimation()
        initMap()
        initListeners()
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

    private fun initListeners(){
        binding.btnToggleMenu.setOnClickListener {
            toggleMenu()
        }
        binding.sideMenu.setOnClickListener {
            toggleMenu()
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
        MapKitFactory.initialize(requireContext())
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

}