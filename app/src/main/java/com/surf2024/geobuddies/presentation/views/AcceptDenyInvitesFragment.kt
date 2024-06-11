package com.surf2024.geobuddies.presentation.views

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.surf2024.geobuddies.R
import com.surf2024.geobuddies.databinding.FragmentAcceptDenyInvitesBinding
import com.surf2024.geobuddies.domain.invites.entities.InviteModel
import com.surf2024.geobuddies.domain.invites.usecases.IOnInviteClickListener
import com.surf2024.geobuddies.domain.main.usecase.FragmentChangeListener
import com.surf2024.geobuddies.presentation.adapters.InvitesRVAdapter
import com.surf2024.geobuddies.presentation.viewmodels.AcceptDenyInvitesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AcceptDenyInvitesFragment : Fragment(), IOnInviteClickListener {
    private lateinit var invitesCloseListener: FragmentChangeListener
    companion object {
        @JvmStatic
        fun newInstance() =
            AcceptDenyInvitesFragment().apply {
                arguments = Bundle().apply {}
            }
    }
    private val binding by viewBinding(FragmentAcceptDenyInvitesBinding::bind)
    private lateinit var invitesViewModel: AcceptDenyInvitesViewModel

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: InvitesRVAdapter

    private var dataList: MutableList<Pair<InviteModel, Boolean>> = mutableListOf()
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
        loadInvites()
    }

    private fun initRecyclerView(){
        recyclerView = binding.invitesRecyclerview
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = InvitesRVAdapter( requireContext(), mutableListOf(), this)
        recyclerView.adapter = adapter
    }
    private fun initInvitesViewModel() {
        Log.d("Hilt", "Creating AcceptDenyInvitesViewModel client instance")
        invitesViewModel = ViewModelProvider(this)[AcceptDenyInvitesViewModel::class.java]
    }
    private fun initObserversInvitesViewModel() {
        invitesViewModel.invitesList.observe(viewLifecycleOwner){ invites->

            if (invites.isEmpty()){
                showToast("there are no invites")
            }
            else{
                if (dataList.isEmpty()) {
                    dataList = invites.map { it to false }.toMutableList()
                }
                adapter.reload(dataList)
            }
        }
        invitesViewModel.isInviteAccepted.observe(viewLifecycleOwner){response ->
            if (response){
                showToast("invite accepted")
                val (invite, _) = dataList[lastAcceptDenyPosition]
                dataList[lastAcceptDenyPosition] = invite to true
                adapter.reload(dataList)
            }
            else{
                showToast("smth went wrong...")
                showToast("try again later")
            }
        }
        invitesViewModel.isInviteDenied.observe(viewLifecycleOwner){response ->
            if (response){
                showToast("invite denied")
                dataList.removeAt(lastAcceptDenyPosition)
                adapter.reload(dataList)
            }
            else{
                showToast("smth went wrong...")
                showToast("try again later")
            }
        }
    }

    private fun loadInvites(){
        invitesViewModel.getAllInvites()
    }
    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onInviteAcceptClick(position: Int) {
        val (invite, _) = dataList[position]
        invitesViewModel.acceptInvite(invite.id)
        lastAcceptDenyPosition = position
    }

    override fun onInviteDenyClick(position: Int) {
        val (invite, _) = dataList[position]
        invitesViewModel.denyInvite(invite.id)
        lastAcceptDenyPosition = position
    }


}