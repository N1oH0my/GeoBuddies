package com.surf2024.geobuddies.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.surf2024.geobuddies.R
import com.surf2024.geobuddies.domain.friends.entity.FriendModel
import de.hdodenhof.circleimageview.CircleImageView

class FriendsRVAdapter(
    private val context: Context,
) : ListAdapter<Pair<FriendModel, Boolean>, FriendsRVAdapter.FriendsViewHolder>(FriendsRVAdapter.FriendDiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FriendsViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.friends_screen_item, parent, false)
        return FriendsViewHolder(view)
    }

    override fun onBindViewHolder(holder: FriendsRVAdapter.FriendsViewHolder, position: Int) {
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
        fun bind(data: Pair<FriendModel, Boolean>){
            userName.text = data.first.name
            userEmail.text = data.first.email
        }
    }

    class FriendDiffCallback : DiffUtil.ItemCallback<Pair<FriendModel, Boolean>>() {
        override fun areItemsTheSame(oldItem: Pair<FriendModel, Boolean>, newItem: Pair<FriendModel, Boolean>): Boolean {
            return oldItem.first.id == newItem.first.id
        }

        override fun areContentsTheSame(oldItem: Pair<FriendModel, Boolean>, newItem: Pair<FriendModel, Boolean>): Boolean {
            return oldItem == newItem
        }
    }

    fun reload(data: List<Pair<FriendModel, Boolean>>){
        submitList(data)
    }
}