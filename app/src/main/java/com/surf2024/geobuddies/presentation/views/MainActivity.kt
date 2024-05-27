package com.surf2024.geobuddies.presentation.views

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.surf2024.geobuddies.R
import com.surf2024.geobuddies.domain.main.usecase.FragmentChangeListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), FragmentChangeListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentHolderId, SplashScreenFragment())
            .commit()

        showToast("Hello!")
        showToast("Welcome to the GeoBuddies!")

        Handler(Looper.getMainLooper()).postDelayed({
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentHolderId, FriendSearchFragment() /*RegistrationFragment()*/)
                .commit()
        }, 3000)

    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onRegistrationComplete() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentHolderId, LoginFragment())
            .commit()
    }

    override fun onLoginComplete() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentHolderId, SplashScreenFragment())
            .commit()
    }
}