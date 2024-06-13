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
    private val _invitesList = MutableLiveData <List<Pair<InviteModel, Boolean>>?>()
    val invitesList: LiveData<List<Pair<InviteModel, Boolean>>?>
        get() = _invitesList
    private fun setInvites(result: List<Pair<InviteModel, Boolean>>?) {
        _invitesList.value = result
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

    fun checkInviteOnPosition(position: Int){
        val currentList = _invitesList.value?.toMutableList()
        if(currentList != null){
            if (position >= 0 && position < currentList.size) {
                val (invite, _) = currentList[position]
                currentList[position] = invite to true

                setInvites(currentList.toList())
            }
        }
    }
    fun removeInviteOnPosition(position: Int){
        val currentList = _invitesList.value?.toMutableList()
        if(currentList != null) {
            if (position >= 0 && position < currentList.size) {
                currentList.removeAt(position)

                setInvites(currentList.toList())
            }
        }
    }

    fun getAllInvites(){
        disposables.clear()
        val disposable = invitesRepository.getAllInvites()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ invites ->
                Log.d("InvitesProcess", "Get successful: $invites")
                setInvites(invites.map { it to false })
            }, { error ->
                Log.e("InvitesProcess", "Get failed", error)

                if (error is HttpException) {
                    Log.d("InvitesProcess", "HTTP Error: ${error.code()}")
                }
                else {
                    Log.d("InvitesProcess", "Error: ${error.message}")
                }
                setInvites(null)
            })
        disposables.add(disposable)
    }

    fun acceptInvite(position: Int){
        val userId = getUserIdByPosition(position)
        if (userId != -1){
            disposables.clear()
            val disposable = acceptInviteRepository.acceptInvite(userId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d("InvitesProcess", "Accept successful")
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
    }

    fun denyInvite(position: Int){
        val userId = getUserIdByPosition(position)
        if (userId != -1) {
            disposables.clear()
            val disposable = denyInviteRepository.denyInvite(userId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d("InvitesProcess", "Deny successful")
                    setDenyInviteResponse(true)
                }, { error ->
                    if (error is HttpException) {
                        Log.d("InvitesProcess", "HTTP Error: ${error.code()}")
                    } else {
                        Log.d("InvitesProcess", "Error: ${error.message}")
                    }
                    setDenyInviteResponse(false)
                })
            disposables.add(disposable)
        }
    }

    private fun getUserIdByPosition(position: Int): Int {
        val currentList = _invitesList.value?.toList()
        if (currentList != null) {
            if (position >= 0 && position < currentList.size) {
                return currentList[position].first.id
            }
        }
        return -1
    }
}