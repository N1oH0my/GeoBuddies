package com.surf2024.geobuddies.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.surf2024.geobuddies.domain.friends.entity.FriendModel
import com.surf2024.geobuddies.domain.friends.repository.IFriendDeleteRepository
import com.surf2024.geobuddies.domain.friends.repository.IFriendsRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import retrofit2.HttpException
import javax.inject.Inject

class FriendsScreenViewModel @Inject constructor(
    private val friendsRepository: IFriendsRepository,
    private val friendsDeleteRepository: IFriendDeleteRepository
) : ViewModel() {
    private val disposables = CompositeDisposable()
    private val _friendsList = MutableLiveData<List<FriendModel>?>()
    val friendsList: LiveData<List<FriendModel>?>
        get() = _friendsList
    private val _isFriendRemoved = MutableLiveData<Boolean>()
    val isFriendRemoved: LiveData<Boolean>
        get() = _isFriendRemoved

    private fun setFriends(result: List<FriendModel>?) {
        _friendsList.value = result
    }

    private fun setRemoveFriendResponse(response: Boolean) {
        _isFriendRemoved.value = response
    }

    fun getAllFriends() {
        disposables.clear()
        val disposable = friendsRepository.getAllFriends()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ friends ->
                Log.d("FriendsProcess", "Get successful: $friends")
                setFriends(friends)
            }, { error ->
                Log.e("FriendsProcess", "Get failed", error)

                if (error is HttpException) {
                    Log.d("FriendsProcess", "HTTP Error: ${error.code()}")
                } else {
                    Log.d("FriendsProcess", "Error: ${error.message}")
                }
                setFriends(null)
            })
        disposables.add(disposable)
    }

    fun removeFriend(position: Int) {
        val userId = getUserIdByPosition(position)
        if (userId != -1) {
            disposables.clear()
            val disposable = friendsDeleteRepository.deleteFriend(userId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d("FriendsProcess", "Deny successful")
                    setRemoveFriendResponse(true)
                }, { error ->
                    if (error is HttpException) {
                        Log.d("FriendsProcess", "HTTP Error: ${error.code()}")
                    } else {
                        Log.d("FriendsProcess", "Error: ${error.message}")
                    }
                    setRemoveFriendResponse(false)
                })
            disposables.add(disposable)
        }
    }

    fun removeFriendOnPosition(position: Int) {
        val currentList = _friendsList.value?.toMutableList()
        if (currentList != null) {
            if (position >= 0 && position < currentList.size) {
                currentList.removeAt(position)

                setFriends(currentList.toList())
            }
        }
    }

    private fun getUserIdByPosition(position: Int): Int {
        val currentList = _friendsList.value?.toList()
        if (currentList != null) {
            if (position >= 0 && position < currentList.size) {
                return currentList[position].id
            }
        }
        return -1
    }
}