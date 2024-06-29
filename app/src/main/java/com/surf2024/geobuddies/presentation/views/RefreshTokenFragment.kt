package com.surf2024.geobuddies.presentation.views

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.surf2024.geobuddies.R
import com.surf2024.geobuddies.domain.main.usecase.RefreshAccessTokenListener
import com.surf2024.geobuddies.presentation.viewmodels.RefreshTokenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RefreshTokenFragment : Fragment() {

    private lateinit var refreshAccessTokenListener: RefreshAccessTokenListener
    private lateinit var refreshTokenViewModel: RefreshTokenViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_refresh_token, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRefreshViewModel()
        initObserversInvitesViewModel()

        getNewAccessToken()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        refreshAccessTokenListener = context as RefreshAccessTokenListener
    }

    private fun initRefreshViewModel() {
        Log.d("Hilt", "Creating RefreshTokenViewModel client instance")
        refreshTokenViewModel = ViewModelProvider(this)[RefreshTokenViewModel::class.java]
    }

    private fun initObserversInvitesViewModel() {
        refreshTokenViewModel.newAccessToken.observe(viewLifecycleOwner) { newToken ->
            saveNewAccessToken(newToken)
        }
        refreshTokenViewModel.accessTokenSaved.observe(viewLifecycleOwner) {
            refreshAccessTokenSuccessful()
        }
        refreshTokenViewModel.error.observe(viewLifecycleOwner) {
            refreshAccessTokenFailed()
        }
    }

    private fun getNewAccessToken() {
        refreshTokenViewModel.refreshAccessToken()
    }

    private fun saveNewAccessToken(newAccessToken: String) {
        refreshTokenViewModel.saveAccessToken(newAccessToken)
    }

    private fun refreshAccessTokenSuccessful() {
        refreshAccessTokenListener.refreshAccessTokenSuccessful()
    }

    private fun refreshAccessTokenFailed() {
        refreshAccessTokenListener.refreshAccessTokenFailed()
    }

}