package com.surf2024.geobuddies.presentation.adapters
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.surf2024.geobuddies.R
import com.surf2024.geobuddies.domain.friendsearch.entity.FoundFriendModel
import com.surf2024.geobuddies.domain.friendsearch.usecases.IOnFriendItemClickListener
import de.hdodenhof.circleimageview.CircleImageView

class FriendSearchRVAdapter(
    private val context: Context,
    private val dataList: List<FoundFriendModel>,
    private val listener: IOnFriendItemClickListener
    ) : RecyclerView.Adapter<FriendSearchRVAdapter.FriendSearchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendSearchViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.found_friend_list_item, parent, false)
        return FriendSearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: FriendSearchViewHolder, position: Int) {
        val data = dataList[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class FriendSearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userName: TextView = itemView.findViewById(R.id.user_name)
        private val userEmail: TextView = itemView.findViewById(R.id.user_email)

        private val addIcon: ImageView = itemView.findViewById(R.id.add_icon)
        private val profileImage: CircleImageView = itemView.findViewById(R.id.profile_image)

        fun bind(data: FoundFriendModel) {
            userName.text = data.name
            userEmail.text = data.email
        }
        init {
            addIcon.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    addIcon.setImageResource(R.drawable.ic_send_invite)
                    listener.onFriendItemClick(position)
                }
            }
        }

    }
}
