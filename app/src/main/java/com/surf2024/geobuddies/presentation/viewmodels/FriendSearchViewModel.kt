package com.surf2024.geobuddies.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.surf2024.geobuddies.domain.friendsearch.entity.FoundFriendModel
import com.surf2024.geobuddies.domain.friendsearch.repository.IFriendSearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class FriendSearchViewModel @Inject constructor(
    private val friendSearchRepository: IFriendSearchRepository
): ViewModel() {
    private val disposables = CompositeDisposable()
    private val _isFriendSearchSuccess = MutableLiveData <List<FoundFriendModel>>()
    val foundFriendList: LiveData<List<FoundFriendModel>>
        get() = _isFriendSearchSuccess
    private fun setFriendSearchSuccess(result: List<FoundFriendModel>) {
        _isFriendSearchSuccess.value = result
    }

    fun findFriend(
        userNameOrEmail: String
    ){
        disposables.clear()
        val disposable = friendSearchRepository.findFriend(
            userNameOrEmail
        )
            .subscribeOn(Schedulers.io())
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
}