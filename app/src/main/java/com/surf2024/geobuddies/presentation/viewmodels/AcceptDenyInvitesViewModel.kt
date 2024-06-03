package com.surf2024.geobuddies.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.surf2024.geobuddies.domain.invites.entities.InviteModel
import com.surf2024.geobuddies.domain.invites.repository.IAcceptInviteRepository
import com.surf2024.geobuddies.domain.invites.repository.IDenyInviteRepository
import com.surf2024.geobuddies.domain.invites.repository.IInvitesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class AcceptDenyInvitesViewModel@Inject constructor(
    private val invitesRepository: IInvitesRepository,
    private val acceptInviteRepository: IAcceptInviteRepository,
    private val denyInviteRepository: IDenyInviteRepository,
): ViewModel() {
    private val disposables = CompositeDisposable()
    private val _InvitesList = MutableLiveData <List<InviteModel>>()
    val invitesList: LiveData<List<InviteModel>>
        get() = _InvitesList
    fun setInvites(result: List<InviteModel>) {
        _InvitesList.value = result
    }

    private val _isInviteAccepted = MutableLiveData<Boolean>()
    val isInviteAccepted: LiveData<Boolean>
        get() = _isInviteAccepted
    private fun setAcceptInviteResponse(response: Boolean){
        _isInviteAccepted.value = response
    }

    private val _isInviteDenied = MutableLiveData<Boolean>()
    val isInviteDenied: LiveData<Boolean>
        get() = _isInviteDenied
    private fun setDenyInviteResponse(response: Boolean){
        _isInviteDenied.value = response
    }

    fun getAllInvites(){
        disposables.clear()
        val disposable = invitesRepository.getAllInvites()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ invites ->
                Log.d("InvitesProcess", "Get successful: $invites")
                setInvites(invites)
            }, { error ->
                Log.e("InvitesProcess", "Get failed", error)

                if (error is HttpException) {
                    Log.d("InvitesProcess", "HTTP Error: ${error.code()}")
                }
                else {
                    Log.d("InvitesProcess", "Error: ${error.message}")
                }
                setInvites(emptyList())
            })
        disposables.add(disposable)
    }

    fun acceptInvite(userId: Int){
        disposables.clear()
        val disposable = acceptInviteRepository.acceptInvite(userId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                Log.d("InvitesProcess", "Accept successful: $response")
                setAcceptInviteResponse(true)
            }, { error->
                if(error is HttpException){
                    Log.d("InvitesProcess", "HTTP Error: ${error.code()}")
                }
                else{
                    Log.d("InvitesProcess", "Error: ${error.message}")
                }
                setAcceptInviteResponse(false)
            })
        disposables.add(disposable)
    }

    fun denyInvite(userId: Int){
        disposables.clear()
        val disposable = denyInviteRepository.denyInvite(userId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                Log.d("InvitesProcess", "Deny successful: $response")
                setDenyInviteResponse(true)
            }, { error->
                if(error is HttpException){
                    Log.d("InvitesProcess", "HTTP Error: ${error.code()}")
                }
                else{
                    Log.d("InvitesProcess", "Error: ${error.message}")
                }
                setDenyInviteResponse(false)
            })
        disposables.add(disposable)
    }

}