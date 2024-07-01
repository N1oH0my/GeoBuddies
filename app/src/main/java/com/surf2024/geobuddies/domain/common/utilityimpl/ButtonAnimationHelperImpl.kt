package com.surf2024.geobuddies.domain.common.utilityimpl

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import com.surf2024.geobuddies.domain.common.utility.IButtonAnimationHelper

class ButtonAnimationHelperImpl: IButtonAnimationHelper {
    @SuppressLint("ClickableViewAccessibility")
    override fun setTouchAnimation(button: View) {
        button.setOnTouchListener { _, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    button.animate()
                        .scaleX(0.8f)
                        .scaleY(0.8f)
                        .alpha(0.4f)
                        .setDuration(150)
                        .start()
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    button.animate()
                        .scaleX(1.0f)
                        .scaleY(1.0f)
                        .alpha(1.0f)
                        .setDuration(150)
                        .start()
                }
            }
            false
        }
    }

}