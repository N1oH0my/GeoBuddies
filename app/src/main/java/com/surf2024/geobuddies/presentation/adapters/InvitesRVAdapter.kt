package com.surf2024.geobuddies.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.surf2024.geobuddies.R
import com.surf2024.geobuddies.domain.invites.entities.InviteModel
import com.surf2024.geobuddies.domain.invites.usecases.IOnInviteClickListener
import de.hdodenhof.circleimageview.CircleImageView

class InvitesRVAdapter(
    private val context: Context,
    private val dataSet: MutableList<Pair<InviteModel, Boolean>>,
    private val listener: IOnInviteClickListener
) : RecyclerView.Adapter<InvitesRVAdapter.InvitesViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): InvitesViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.accept_deny_invites_list_item, parent, false)
        return InvitesViewHolder(view)
    }

    override fun onBindViewHolder(holder: InvitesRVAdapter.InvitesViewHolder, position: Int) {
        val data = dataSet[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    fun reload(data: List<Pair<InviteModel, Boolean>>){
        dataSet.clear()
        dataSet.addAll(data)
        notifyDataSetChanged()
    }
    inner class InvitesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userName: TextView = itemView.findViewById(R.id.invite_user_name)
        private val userEmail: TextView = itemView.findViewById(R.id.invite_user_email)

        private val addIcon: ImageView = itemView.findViewById(R.id.invite_add_icon)
        private val crossIcon: ImageView = itemView.findViewById(R.id.invite_cross_icon)
        private val profileImage: CircleImageView = itemView.findViewById(R.id.invite_profile_image)

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
    }
}