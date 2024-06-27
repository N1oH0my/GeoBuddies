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
import com.surf2024.geobuddies.domain.friends.entity.FriendModel
import com.surf2024.geobuddies.domain.friends.usecases.IOnFriendRemoveClickListener
import de.hdodenhof.circleimageview.CircleImageView

class FriendsAdapter(
    private val context: Context,
    private val listener: IOnFriendRemoveClickListener
) : ListAdapter<FriendModel, FriendsAdapter.FriendsViewHolder>(FriendsAdapter.FriendDiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FriendsViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.friends_screen_item, parent, false)
        return FriendsViewHolder(view)
    }

    override fun onBindViewHolder(holder: FriendsAdapter.FriendsViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    inner class FriendsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userName: TextView = itemView.findViewById(R.id.friend_user_name)
        private val userEmail: TextView = itemView.findViewById(R.id.friend_user_email)
        private val profileImage: CircleImageView = itemView.findViewById(R.id.friend_profile_image)

        private val crossIcon: ImageView = itemView.findViewById(R.id.friend_cross_icon)

        init {
            crossIcon.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onFriendRemoveClick(position)
                }
            }
        }

        fun bind(data: FriendModel) {
            userName.text = data.name
            userEmail.text = data.email
        }

        fun hideCrossIcon() {
            crossIcon.visibility = View.GONE
        }

        fun showCrossIcon() {
            crossIcon.visibility = View.VISIBLE
        }
    }

    class FriendDiffCallback : DiffUtil.ItemCallback<FriendModel>() {
        override fun areItemsTheSame(oldItem: FriendModel, newItem: FriendModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FriendModel, newItem: FriendModel): Boolean {
            return oldItem == newItem
        }
    }

    fun reload(data: List<FriendModel>) {
        submitList(data)
    }
}