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
import com.surf2024.geobuddies.domain.friendsearch.usecases.IOnFriendClickListener
import com.surf2024.geobuddies.domain.main.usecase.FragmentChangeListener
import com.surf2024.geobuddies.presentation.adapters.FriendSearchRVAdapter
import com.surf2024.geobuddies.presentation.viewmodels.FriendSearchViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FriendSearchFragment : Fragment(), IOnFriendClickListener {
    private lateinit var friendSearchCloseListener: FragmentChangeListener
    companion object {
        @JvmStatic
        fun newInstance() =
            FriendSearchFragment().apply {
                arguments = Bundle().apply {}
            }
    }
    private val binding by viewBinding(FragmentFriendSearchBinding::bind)
    private lateinit var friendSearchViewModel: FriendSearchViewModel

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FriendSearchRVAdapter

    private lateinit var dataList: List<FoundFriendModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        friendSearchCloseListener = context as FragmentChangeListener
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_friend_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFriendSearchViewModel()
        initRecyclerView()
        initListenerSearchFriendField()
        initObserversFriendSearchViewModel()

        overrideOnBackPressed()
    }



    override fun onFriendItemClick(position: Int) {
        val clickedFriend = dataList[position]
        friendSearchViewModel.inviteFriend(clickedFriend.id)
    }

    private fun initFriendSearchViewModel(){
        Log.d("Hilt", "Creating friendSearchViewModel client instance")
        friendSearchViewModel = ViewModelProvider(this)[FriendSearchViewModel::class.java]
    }
    private fun initRecyclerView(){
        recyclerView = binding.foudFriendRecyclerview
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter = FriendSearchRVAdapter(requireContext(), this)
        recyclerView.adapter = adapter
    }
    private fun initListenerSearchFriendField() {
        binding.editTextSearch.editText?.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                event.action == KeyEvent.ACTION_DOWN
                ) {
                val userNameOrEmail = binding.editTextSearch.editText?.text.toString()
                friendSearchViewModel.findFriend(userNameOrEmail)
                true
            } else {
                false
            }
        }
    }

    private fun initObserversFriendSearchViewModel() {
        friendSearchViewModel.foundFriendList.observe(viewLifecycleOwner){  foundFriendList ->
            adapter.reload(foundFriendList)
            dataList = foundFriendList
            if (dataList.isEmpty()){
                showToast("no users found or check auth")
            }
        }

        friendSearchViewModel.isInviteSendSuccess.observe(viewLifecycleOwner){result ->
            if (result){
                showToast("invite sent")
            }
            else{
                showToast("smth went wrong...")
                showToast("try again later")
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
    private fun onSearchFriendClose() {
        friendSearchCloseListener.onSearchFriendClose()
    }
    fun overrideOnBackPressed() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onSearchFriendClose()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
}