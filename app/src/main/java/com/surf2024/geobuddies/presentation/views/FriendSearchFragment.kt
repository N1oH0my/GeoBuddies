package com.surf2024.geobuddies.presentation.views

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.surf2024.geobuddies.R
import com.surf2024.geobuddies.databinding.FragmentFriendSearchBinding
import com.surf2024.geobuddies.domain.friendsearch.entity.FoundFriendModel
import com.surf2024.geobuddies.domain.friendsearch.usecases.IOnFriendItemClickListener
import com.surf2024.geobuddies.presentation.adapters.FriendSearchRVAdapter
import com.surf2024.geobuddies.presentation.viewmodels.FriendSearchViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FriendSearchFragment : Fragment(), IOnFriendItemClickListener {
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

        /*dataList= listOf(
            FoundFriendModel(1, "Name Surname 1", "email1@example.com", "", ""),
            FoundFriendModel(2, "Name Surname 2", "email2@example.com", "", ""),
        )

        adapter = FriendSearchRVAdapter(requireContext(), dataList, this)
        recyclerView.adapter = adapter*/

    }

    override fun onFriendItemClick(position: Int) {
        val clickedFriend = dataList[position]

    }

    private fun initFriendSearchViewModel(){
        Log.d("Hilt", "Creating friendSearchViewModel client instance")
        friendSearchViewModel = ViewModelProvider(this)[FriendSearchViewModel::class.java]
    }
    private fun initRecyclerView(){
        recyclerView = binding.foudFriendRecyclerview
        recyclerView.layoutManager = LinearLayoutManager(context)
    }
    private fun initListenerSearchFriendField() {
        binding.editTextSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                event.action == KeyEvent.ACTION_DOWN
                ) {
                val userNameOrEmail = binding.editTextSearch.text.toString()
                friendSearchViewModel.findFriend(userNameOrEmail)
                true
            } else {
                false
            }
        }
    }

    private fun initObserversFriendSearchViewModel() {
        friendSearchViewModel.foundFriendList.observe(viewLifecycleOwner){  foundFriendList ->
            adapter = FriendSearchRVAdapter(requireContext(), foundFriendList, this)
            recyclerView.adapter = adapter

            dataList = foundFriendList
        }
    }
}