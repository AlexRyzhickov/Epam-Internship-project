package com.epam.bet.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.epam.bet.R
import com.epam.bet.entities.User

class FollowersRecyclerAdapter(private val followersArray: List<User?>): RecyclerView.Adapter<FollowersRecyclerAdapter.FollowersViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FollowersRecyclerAdapter.FollowersViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.followers_recycler_row, parent, false)
        return FollowersRecyclerAdapter.FollowersViewHolder(v)
    }

    override fun onBindViewHolder(
        holder: FollowersRecyclerAdapter.FollowersViewHolder,
        position: Int
    ) {
        val user = followersArray[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int {
        return followersArray.size
    }

    class FollowersViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(user: User?) {
            print("new user arrived! ${user?.name}")
            val textName = itemView.findViewById(R.id.username) as TextView
            val textEmail = itemView.findViewById(R.id.email) as TextView
            textName.text = user?.name
            textEmail.text = user?.email

        }
    }

}