package com.surf2024.geobuddies.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.surf2024.geobuddies.domain.friends.entity.FriendModel
import com.surf2024.geobuddies.domain.friends.repository.IFriendsRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import retrofit2.HttpException
import javax.inject.Inject

class FriendsScreenViewModel@Inject constructor(
    private val friendsRepository: IFriendsRepository
):ViewModel() {
    private val disposables = CompositeDisposable()
    private val _friendsList = MutableLiveData <List<Pair<FriendModel, Boolean>>?>()
    val friendsList: LiveData<List<Pair<FriendModel, Boolean>>?>
        get() = _friendsList
    private fun setFriends(result: List<Pair<FriendModel, Boolean>>?) {
        _friendsList.value = result
    }

    fun getAllFriends(){
        disposables.clear()
        val disposable = friendsRepository.getAllFriends()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ friends ->
                Log.d("FriendsProcess", "Get successful: $friends")
                setFriends(friends.map { it to false })
            }, { error ->
                Log.e("FriendsProcess", "Get failed", error)

                if (error is HttpException) {
                    Log.d("FriendsProcess", "HTTP Error: ${error.code()}")
                }
                else {
                    Log.d("FriendsProcess", "Error: ${error.message}")
                }
                setFriends(null)
            })
        disposables.add(disposable)
    }
}