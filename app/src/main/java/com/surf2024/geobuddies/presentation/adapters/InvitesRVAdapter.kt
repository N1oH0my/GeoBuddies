package com.surf2024.geobuddies.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.surf2024.geobuddies.R
import com.surf2024.geobuddies.domain.friendsearch.usecases.IOnFriendItemClickListener
import com.surf2024.geobuddies.domain.invites.entities.InviteModel

class InvitesRVAdapter(
    private val context: Context,
    private val dataList: List<InviteModel>,
    private val listener: IOnFriendItemClickListener
) : RecyclerView.Adapter<InvitesRVAdapter.InvitesViewHolder>() {
    inner class InvitesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(data: InviteModel){

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): InvitesViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.accept_deny_invites_list_item, parent, false)
        return InvitesViewHolder(view)
    }

    override fun onBindViewHolder(holder: InvitesRVAdapter.InvitesViewHolder, position: Int) {
        val data = dataList[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

}