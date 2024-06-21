package com.surf2024.geobuddies.presentation.views

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.surf2024.geobuddies.R
import com.surf2024.geobuddies.databinding.FragmentAcceptDenyInvitesBinding
import com.surf2024.geobuddies.domain.invites.usecases.IOnInviteClickListener
import com.surf2024.geobuddies.domain.main.usecase.FragmentChangeListener
import com.surf2024.geobuddies.presentation.adapters.InvitesRVAdapter
import com.surf2024.geobuddies.presentation.viewmodels.AcceptDenyInvitesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AcceptDenyInvitesFragment : Fragment(), IOnInviteClickListener {
    companion object {
        @JvmStatic
        fun newInstance() =
            AcceptDenyInvitesFragment().apply {
                arguments = Bundle().apply {}
            }
    }
    private lateinit var invitesCloseListener: FragmentChangeListener

    private val binding by viewBinding(FragmentAcceptDenyInvitesBinding::bind)
    private lateinit var invitesViewModel: AcceptDenyInvitesViewModel

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: InvitesRVAdapter

    private var lastAcceptDenyPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        invitesCloseListener = context as FragmentChangeListener
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
        initInvitesViewModel()
        initObserversInvitesViewModel()

        overrideOnBackPressed()

        loadInvites()
    }
    override fun onInviteAcceptClick(position: Int) {
        invitesViewModel.acceptInvite(position)
        lastAcceptDenyPosition = position
    }

    override fun onInviteDenyClick(position: Int) {
        invitesViewModel.denyInvite(position)
        lastAcceptDenyPosition = position
    }

    private fun overrideOnBackPressed() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onInvitesClose()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun initRecyclerView(){
        recyclerView = binding.invitesRecyclerview
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = InvitesRVAdapter( requireContext(), this)
        recyclerView.adapter = adapter
    }

    private fun initInvitesViewModel() {
        Log.d("Hilt", "Creating AcceptDenyInvitesViewModel client instance")
        invitesViewModel = ViewModelProvider(this)[AcceptDenyInvitesViewModel::class.java]
    }

    private fun initObserversInvitesViewModel() {
        invitesViewModel.invitesList.observe(viewLifecycleOwner){ invites->

            if (invites != null) {
                if (invites.isEmpty()){
                    activity?.let { showToast(it.getString(R.string.no_invites)) }
                }
                adapter.reload(invites)
            }
            else{
                showError()
            }
        }
        invitesViewModel.isInviteAccepted.observe(viewLifecycleOwner){response ->
            if (response){
                activity?.let { showToast(it.getString(R.string.invite_accepted)) }
                invitesViewModel.checkInviteOnPosition(lastAcceptDenyPosition)
            }
            else{
                showError()
            }
        }
        invitesViewModel.isInviteDenied.observe(viewLifecycleOwner){response ->
            if (response){
                activity?.let { showToast(it.getString(R.string.invite_denied)) }
                invitesViewModel.removeInviteOnPosition(lastAcceptDenyPosition)
            }
            else{
                showError()
            }
        }
    }

    private fun loadInvites(){
        invitesViewModel.getAllInvites()
    }

    private fun onInvitesClose() {
        invitesCloseListener.onInvitesClose()
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