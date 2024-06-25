package com.surf2024.geobuddies.presentation.views

import android.content.Context
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
import java.util.Locale

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

        setLocationLanguage("en")

        startUpLoading()

    }

    override fun onRegistrationComplete() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentHolderId, LoginFragment())
            .commit()
    }

    override fun onLoginComplete() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentHolderId, MapFragment())
            .commit()
    }

    override fun onSignUpClicked() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentHolderId, RegistrationFragment())
            .commit()
    }
    override fun openFriends() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentHolderId, MapFragment())
            .commit()
    }
    override fun onFriendsClose() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentHolderId, MapFragment())
            .commit()
    }

    override fun openFriendsSearch() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentHolderId, FriendSearchFragment())
            .commit()
    }
    override fun onFriendsSearchClose() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentHolderId, MapFragment())
            .commit()
    }

    override fun onRegistrationBack() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentHolderId, LoginFragment())
            .commit()
    }

    override fun openInvites() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentHolderId, FriendSearchFragment())
            .commit()
    }
    override fun onInvitesClose() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentHolderId, MapFragment())
            .commit()
    }

    override fun onMapClose() {
        finishAffinity()
    }

    override fun onLogOut() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentHolderId, SplashScreenFragment())
            .commit()

        Handler(Looper.getMainLooper()).postDelayed({
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentHolderId, LoginFragment())
                .commit()
        }, 2000)
    }

    private fun startUpLoading() {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentHolderId, SplashScreenFragment())
            .commit()

        greetingMessageShow()

        Handler(Looper.getMainLooper()).postDelayed({
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentHolderId, LoginFragment())
                .commit()
        }, 3500)
    }

    private fun setLocationLanguage(language: String){
        val context: Context = applicationContext
        context.resources.configuration.setLocale(Locale("en"))
    }

    private fun greetingMessageShow(){
        showToast(this.getString(R.string.hello))
        showToast(this.getString(R.string.welcome_to_the_geo_buddies))
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
