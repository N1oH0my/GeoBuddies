package com.surf2024.geobuddies.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.surf2024.geobuddies.domain.invites.entities.InviteModel
import com.surf2024.geobuddies.domain.invites.repository.IInvitesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class AcceptDenyInvitesViewModel@Inject constructor(
    private val invitesRepository: IInvitesRepository
): ViewModel() {
    private val disposables = CompositeDisposable()
    private val _InvitesList = MutableLiveData <List<InviteModel>>()
    val invitesList: LiveData<List<InviteModel>>
        get() = _InvitesList
    private fun setInvites(result: List<InviteModel>) {
        _InvitesList.value = result
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

}