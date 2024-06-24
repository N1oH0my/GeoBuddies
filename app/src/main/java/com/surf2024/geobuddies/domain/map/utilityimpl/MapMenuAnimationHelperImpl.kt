package com.surf2024.geobuddies.domain.map.utilityimpl

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.view.View
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.content.ContextCompat
import com.surf2024.geobuddies.R
import com.surf2024.geobuddies.databinding.FragmentMapBinding
import com.surf2024.geobuddies.domain.map.utility.IMapMenuAnimationHelper

class MapMenuAnimationHelperImpl(
    private val context: Context,
    private val binding: FragmentMapBinding,
): IMapMenuAnimationHelper {
    private lateinit var mapShadowAnimation: ValueAnimator
    private lateinit var slideInAnimator: ValueAnimator
    private lateinit var slideOutAnimator: ValueAnimator
    private lateinit var fadeInAnimator: ValueAnimator
    private lateinit var fadeOutAnimator: ValueAnimator

    private var isMenuOpen = false

    init {
        initAnimation()
    }

    override fun animateMapMenu() {
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
    private fun initAnimation(){
        val initialColor = 0x00000000
        val finalColor = ContextCompat.getColor(context, R.color.menu_map_shadow)

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
    private fun startMapShadowAnimation() {
        if (::mapShadowAnimation.isInitialized) {
            if (!isMenuOpen){
                mapShadowAnimation.start()
            } else{
                mapShadowAnimation.reverse()
            }
        }
    }
}