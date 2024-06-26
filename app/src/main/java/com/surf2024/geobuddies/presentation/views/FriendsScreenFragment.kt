package com.surf2024.geobuddies.presentation.views

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.surf2024.geobuddies.R
import com.surf2024.geobuddies.databinding.FragmentFriendsScreenBinding
import com.surf2024.geobuddies.domain.main.usecase.FragmentChangeListener
import com.surf2024.geobuddies.presentation.adapters.FriendsRVAdapter
import com.surf2024.geobuddies.presentation.viewmodels.FriendsScreenViewModel

class FriendsScreenFragment: Fragment() {
    companion object {
        @JvmStatic
        fun newInstance() =
            AcceptDenyInvitesFragment().apply {
                arguments = Bundle().apply {}
            }
    }
    private lateinit var friendsCloseListener: FragmentChangeListener
    private lateinit var friendsViewModel: FriendsScreenViewModel

    private val binding by viewBinding(FragmentFriendsScreenBinding::bind)

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FriendsRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        friendsCloseListener = context as FragmentChangeListener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_friends_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        initFriendsViewModel()
        initObserversFriendsViewModel()

        overrideOnBackPressed()

        loadFriends()
    }

    private fun overrideOnBackPressed() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onFriendsClose()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
    private fun initRecyclerView(){
        recyclerView = binding.friendsRecyclerview
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = FriendsRVAdapter( requireContext())
        recyclerView.adapter = adapter
    }
    private fun initFriendsViewModel() {
        Log.d("Hilt", "Creating FriendsScreenViewModel client instance")
        friendsViewModel = ViewModelProvider(this)[FriendsScreenViewModel::class.java]
    }

    private fun initObserversFriendsViewModel() {
        friendsViewModel.friendsList.observe(viewLifecycleOwner){ friends->

            if (friends != null) {
                if (friends.isEmpty()){
                    activity?.let { showToast(it.getString(R.string.no_frineds)) }
                }
                adapter.reload(friends)
            }
            else{
                showError()
            }
        }
    }

    private fun loadFriends(){
        friendsViewModel.getAllFriends()
    }
    private fun onFriendsClose() {
        friendsCloseListener.onFriendsClose()
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showError(){
        activity?.let {
            showToast(it.getString(R.string.error_part_1))
            showToast(it.getString(R.string.error_part_2))
        }
    }

}