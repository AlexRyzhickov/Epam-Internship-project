package com.epam.bet.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.epam.bet.R
import com.epam.bet.entities.Follower
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class UserDataAdapter(options: FirestoreRecyclerOptions<Follower>) : FirestoreRecyclerAdapter<Follower, UserDataAdapter.FollowersViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowersViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
                R.layout.followers_recycler_row,
                parent, false
        )
        return FollowersViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FollowersViewHolder, position: Int, model: Follower) {
        holder.textName.text = model.name
        holder.textEmail.text = model.email
    }

    class FollowersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textName: TextView
        val textEmail: TextView

        init {
            textName = itemView.findViewById(R.id.username)
            textEmail = itemView.findViewById(R.id.email)
        }
    }
}