package com.surf2024.geobuddies.presentation.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.surf2024.geobuddies.R
import com.surf2024.geobuddies.domain.friendsearch.entity.FoundFriendModel
import com.surf2024.geobuddies.domain.friendsearch.usecases.IOnFriendItemClickListener
import com.surf2024.geobuddies.presentation.adapters.FriendSearchRVAdapter


class FriendSearchFragment : Fragment(), IOnFriendItemClickListener {

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
        recyclerView = view.findViewById(R.id.foud_friend_recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(context)

        dataList= listOf(
            FoundFriendModel(1, "Name Surname 1", "email1@example.com", "", ""),
            FoundFriendModel(2, "Name Surname 2", "email2@example.com", "", ""),
        )

        adapter = FriendSearchRVAdapter(requireContext(), dataList, this)
        recyclerView.adapter = adapter

    }

    companion object {

        @JvmStatic
        fun newInstance() =
            FriendSearchFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onFriendItemClick(position: Int) {
        val clickedFriend = dataList[position]

    }
}