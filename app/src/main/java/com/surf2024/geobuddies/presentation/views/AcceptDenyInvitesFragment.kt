package com.surf2024.geobuddies.presentation.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.surf2024.geobuddies.R
import com.surf2024.geobuddies.databinding.FragmentAcceptDenyInvitesBinding

class AcceptDenyInvitesFragment : Fragment() {

    private val binding by viewBinding(FragmentAcceptDenyInvitesBinding::bind)
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_accept_deny_invites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
    }

    private fun initRecyclerView(){
        recyclerView = binding.invitesRecyclerview
        recyclerView.layoutManager = LinearLayoutManager(context)
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AcceptDenyInvitesFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}