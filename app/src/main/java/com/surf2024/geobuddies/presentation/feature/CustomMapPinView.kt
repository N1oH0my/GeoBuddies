package com.surf2024.geobuddies.presentation.feature

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.BitmapFactory
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.surf2024.geobuddies.R
import com.surf2024.geobuddies.presentation.viewmodels.map.CustomMapPinViewModel
import de.hdodenhof.circleimageview.CircleImageView

class CustomMapPinView @JvmOverloads constructor(
    _context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(_context, attrs, defStyleAttr) {

    val viewModel by activityViewModels<CustomMapPinViewModel>()

    private var _mapPinImageView: ImageView

    private var _mapPinProfileImage: CircleImageView

    init {
        LayoutInflater.from(_context).inflate(R.layout.custom_map_pin, this, true)
        _mapPinImageView = findViewById(R.id.custom_map_pin_imageview)
        _mapPinProfileImage = findViewById(R.id.custom_map_pin_profile_image)
        initListeners()
    }

    fun setProfileImageFromUrl(url: String) {
        viewModel.getAvatar(url)
    }

    private fun initListeners() {
        viewModel.getAvatarSuccess.observeForever {
            setProfileImageFromByteArray(it.imageFile)
        }
    }

    fun setProfileImageFromByteArray(imageFile: ByteArray) {
        val bitmap = BitmapFactory.decodeByteArray(imageFile, 0, imageFile.size)
        _mapPinProfileImage.setImageBitmap(bitmap)
    }
}

fun Context.getActivity(): Activity? {
    return when (this) {
        is ContextWrapper -> {
            when (this) {
                is Activity -> this
                else -> this.baseContext.getActivity()
            }
        }

        else -> null
    }
}

fun View.findActivityViewModelStoreOwner(): ViewModelStoreOwner {
    val activity = context.getActivity()
    return (activity as? ComponentActivity)
        ?: error("$activity is not inheritance from ComponentActivity")
}

inline fun <reified Vm : ViewModel> View.activityViewModels() = lazy {
    ViewModelProvider(findActivityViewModelStoreOwner())[Vm::class.java]
}