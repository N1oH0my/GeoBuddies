package com.surf2024.geobuddies.presentation.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.surf2024.geobuddies.R


class FriendSearchFragment : Fragment() {

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

    companion object {

        @JvmStatic
        fun newInstance() =
            FriendSearchFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}