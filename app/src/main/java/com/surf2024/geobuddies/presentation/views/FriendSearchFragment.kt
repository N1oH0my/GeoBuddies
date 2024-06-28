package com.surf2024.geobuddies.presentation.views

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.surf2024.geobuddies.R
import com.surf2024.geobuddies.databinding.FragmentFriendSearchBinding
import com.surf2024.geobuddies.domain.friendsearch.entity.FoundFriendModel
import com.surf2024.geobuddies.domain.friendsearch.usecases.IOnFriendItemClickListener
import com.surf2024.geobuddies.domain.main.usecase.FragmentChangeListener
import com.surf2024.geobuddies.presentation.adapters.FriendSearchAdapter
import com.surf2024.geobuddies.presentation.viewmodels.FriendSearchViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FriendSearchFragment : Fragment(), IOnFriendItemClickListener {

    private lateinit var friendSearchCloseListener: FragmentChangeListener

    private val binding by viewBinding(FragmentFriendSearchBinding::bind)

    private lateinit var friendSearchViewModel: FriendSearchViewModel

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FriendSearchAdapter

    private lateinit var dataList: List<FoundFriendModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_friend_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        overrideOnBackPressed()

        initFriendSearchViewModel()

        initRecyclerView()

        initListenerSearchFriendField()

        initObserversFriendSearchViewModel()

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        friendSearchCloseListener = context as FragmentChangeListener
    }

    override fun onFriendItemClick(position: Int) {
        val clickedFriend = dataList[position]
        friendSearchViewModel.inviteFriend(clickedFriend.id)
    }

    fun overrideOnBackPressed() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onSearchFriendClose()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun initFriendSearchViewModel() {
        Log.d("Hilt", "Creating friendSearchViewModel client instance")
        friendSearchViewModel = ViewModelProvider(this)[FriendSearchViewModel::class.java]
    }

    private fun initRecyclerView() {
        recyclerView = binding.foudFriendRecyclerview
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter = FriendSearchAdapter(requireContext(), this)
        recyclerView.adapter = adapter
    }

    private fun initListenerSearchFriendField() {
        binding.editTextSearch.editText?.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE || event.action == KeyEvent.ACTION_DOWN) {
                val userNameOrEmail = binding.editTextSearch.editText?.text.toString()
                friendSearchViewModel.findFriend(userNameOrEmail)
                true
            } else {
                false
            }
        }
    }

    private fun initObserversFriendSearchViewModel() {
        friendSearchViewModel.foundFriendList.observe(viewLifecycleOwner) { foundFriendList ->
            updateFoundFriends(foundFriendList)
        }
        friendSearchViewModel.isInviteSendSuccess.observe(viewLifecycleOwner) {
            showInviteSent()
        }
        friendSearchViewModel.serverError.observe(viewLifecycleOwner) {
            showError()
        }
    }

    private fun updateFoundFriends(foundFriendList: List<FoundFriendModel>) {
        adapter.reload(foundFriendList)
        dataList = foundFriendList
        if (foundFriendList.isEmpty()) {
            showNoUsersFound()
        }
    }

    private fun onSearchFriendClose() {
        friendSearchCloseListener.onFriendsSearchClose()
    }
    private fun showNoUsersFound() {
        activity?.let { showToast(it.getString(R.string.no_users_found)) }
    }

    private fun showInviteSent() {
        activity?.let { showToast(it.getString(R.string.invite_sent)) }
    }

    private fun showError() {
        activity?.let {
            showToast(it.getString(R.string.error_part_1))
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

}