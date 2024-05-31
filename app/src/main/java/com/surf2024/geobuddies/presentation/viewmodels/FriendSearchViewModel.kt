package com.surf2024.geobuddies.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.surf2024.geobuddies.domain.friendsearch.entity.FoundFriendModel
import com.surf2024.geobuddies.domain.friendsearch.repository.IFriendSearchRepository
import com.surf2024.geobuddies.domain.friendsearch.repository.IInviteSendRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class FriendSearchViewModel @Inject constructor(
    private val friendSearchRepository: IFriendSearchRepository,
    private val inviteSendRepository: IInviteSendRepository,
): ViewModel() {
    private val disposables = CompositeDisposable()
    private val _isFriendSearchSuccess = MutableLiveData <List<FoundFriendModel>>()
    val foundFriendList: LiveData<List<FoundFriendModel>>
        get() = _isFriendSearchSuccess
    private fun setFriendSearchSuccess(result: List<FoundFriendModel>) {
        _isFriendSearchSuccess.value = result
    }

    private val _isInviteSendSuccess = MutableLiveData <Boolean>()
    val isInviteSendSuccess: LiveData<Boolean>
        get() = _isInviteSendSuccess
    private fun setInviteSendSuccess(result: Boolean) {
        _isInviteSendSuccess.value = result
    }

    fun findFriend(
        userNameOrEmail: String
    ){
        disposables.clear()
        val disposable = friendSearchRepository.findFriend(
            userNameOrEmail
        )
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ foundFriends  ->
                Log.d("FriendSearchProcess", "Search successful: $foundFriends")
                setFriendSearchSuccess(foundFriends)
            }, { error ->
                Log.e("FriendSearchProcess", "Search failed", error)

                if (error is HttpException) {
                    Log.d("FriendSearchProcess", "HTTP Error: ${error.code()}")
                }
                else {
                    Log.d("FriendSearchProcess", "Error: ${error.message}")
                }
                setFriendSearchSuccess(emptyList())
            })
        disposables.add(disposable)
    }

    fun inviteFriend(
        userId: Int
    ){
        disposables.clear()
        val disposable = inviteSendRepository.inviteFriend(
            userId
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ isSuccess ->
                Log.d("InviteSendProcess", "Invite successful: $isSuccess")
                setInviteSendSuccess(true)
            }, { error ->
                Log.e("InviteSendProcess", "Search failed", error)

                if (error is HttpException) {
                    Log.d("InviteSendProcess", "HTTP Error: ${error.code()}")
                }
                else {
                    Log.d("InviteSendProcess", "Error: ${error.message}")
                }
                setInviteSendSuccess(false)
            })
        disposables.add(disposable)
    }
}