package com.surf2024.geobuddies.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.surf2024.geobuddies.R
import com.surf2024.geobuddies.domain.invites.entities.InviteModel
import com.surf2024.geobuddies.domain.invites.usecases.IOnInviteClickListener
import de.hdodenhof.circleimageview.CircleImageView

class InvitesRVAdapter(
    private val context: Context,
    private val listener: IOnInviteClickListener
) : ListAdapter<Pair<InviteModel, Boolean>, InvitesRVAdapter.InvitesViewHolder>(InviteDiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): InvitesViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.accept_deny_invites_list_item, parent, false)
        return InvitesViewHolder(view)
    }

    override fun onBindViewHolder(holder: InvitesRVAdapter.InvitesViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    inner class InvitesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userName: TextView = itemView.findViewById(R.id.invite_user_name)
        private val userEmail: TextView = itemView.findViewById(R.id.invite_user_email)

        private val addIcon: ImageView = itemView.findViewById(R.id.invite_add_icon)
        private val crossIcon: ImageView = itemView.findViewById(R.id.invite_cross_icon)
        private val profileImage: CircleImageView = itemView.findViewById(R.id.invite_profile_image)

        init {
            addIcon.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onInviteAcceptClick(position)
                }
            }
            crossIcon.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onInviteDenyClick(position)
                }
            }
        }

        fun bind(data: Pair<InviteModel, Boolean>){
            userName.text = data.first.name
            userEmail.text = data.first.email

            if (data.second){
                addIcon.setImageResource(R.drawable.ic_send_invite)
                hideCrossIcon()
            }
            else{
                addIcon.setImageResource(R.drawable.ic_add_friend)
                showCrossIcon()
                crossIcon.setImageResource(R.drawable.ic_cross)
            }
        }

        fun hideCrossIcon() {
            crossIcon.visibility = View.GONE
        }

        fun showCrossIcon() {
            crossIcon.visibility = View.VISIBLE
        }

    }

    class InviteDiffCallback : DiffUtil.ItemCallback<Pair<InviteModel, Boolean>>() {
        override fun areItemsTheSame(oldItem: Pair<InviteModel, Boolean>, newItem: Pair<InviteModel, Boolean>): Boolean {
            return oldItem.first == newItem.first
        }

        override fun areContentsTheSame(oldItem: Pair<InviteModel, Boolean>, newItem: Pair<InviteModel, Boolean>): Boolean {
            return oldItem == newItem
        }
    }

    fun reload(data: List<Pair<InviteModel, Boolean>>){
        submitList(data)
    }
}