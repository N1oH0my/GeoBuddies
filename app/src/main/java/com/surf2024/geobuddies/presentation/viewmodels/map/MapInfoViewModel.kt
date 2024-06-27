package com.surf2024.geobuddies.presentation.viewmodels.map

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.surf2024.geobuddies.domain.common.repository.IUserInfoRepository
import com.surf2024.geobuddies.domain.friends.entitity.FriendModel
import com.surf2024.geobuddies.domain.friends.repository.IFriendsRepository
import com.surf2024.geobuddies.domain.login.entity.UserInfoModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class MapInfoViewModel @Inject constructor(
    private val getFriendRepository: IFriendsRepository,
    private val getUserInfoRepository: IUserInfoRepository,
) : ViewModel() {

    private val friendsDisposable: CompositeDisposable = CompositeDisposable()

    private val _friendList = MutableLiveData<List<FriendModel>>()
    val friendList: LiveData<List<FriendModel>>
        get() = _friendList

    private val _user = MutableLiveData<UserInfoModel>()
    val user: LiveData<UserInfoModel>
        get() = _user

    private val _serverError = MutableLiveData<Boolean>()
    val serverError: LiveData<Boolean>
        get() = _serverError

    fun getFriends() {
        friendsDisposable.clear()
        val disposable =
            getFriendRepository.getAllFriends().observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    Log.d("GeoProccess", "Get friends successful $result")
                    setFriendList(result)
                }, { error ->
                    Log.d("GeoProccess", "Get failed $error")
                    if (error is HttpException) {
                        Log.d("GeoProccess", "Get failed ${error.code()}")
                    } else {
                        Log.d("GeoProccess", "Get failed ${error.message}")
                    }
                    setServerError()
                })
        friendsDisposable.add(disposable)
    }

    fun getUserInfo() {
        val user = getUserInfoRepository.getUserInfo()
        if(user != null) {
            setUserInfo(user)
        } else {
            setServerError()
        }
    }

    private fun setFriendList(data: List<FriendModel>) {
        _friendList.value = data
    }

    private fun setUserInfo(data: UserInfoModel) {
        _user.value = data
    }

    private fun setServerError(){
        if (_serverError.value != true){
            _serverError.value = true
        }
    }

}