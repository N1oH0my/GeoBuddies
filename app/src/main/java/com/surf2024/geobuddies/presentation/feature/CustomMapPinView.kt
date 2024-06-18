package com.surf2024.geobuddies.presentation.feature

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.surf2024.geobuddies.R
import de.hdodenhof.circleimageview.CircleImageView

class CustomMapPinView@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr)  {
    private var _mapPinImageView: ImageView
    val mapPinImageView: ImageView
        get() = _mapPinImageView

    private var _mapPinProfileImage: CircleImageView
    val mapPinProfileImage: CircleImageView
        get() = _mapPinProfileImage
    init {
        LayoutInflater.from(context).inflate(R.layout.custom_map_pin, this, true)
        _mapPinImageView = findViewById(R.id.custom_map_pin_imageview)
        _mapPinProfileImage = findViewById(R.id.custom_map_pin_profile_image)
    }

    fun setProfileImageDrawable(drawable: Drawable?) {
        mapPinProfileImage.setImageDrawable(drawable)
    }
    fun setProfileImageFromUrl(url: String) {
        Glide.with(context).load(url).into(mapPinProfileImage)
    }
}