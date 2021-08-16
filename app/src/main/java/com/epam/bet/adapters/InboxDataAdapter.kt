package com.epam.bet.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.epam.bet.R
import com.epam.bet.entities.Follower
import com.epam.bet.entities.InboxMessage
import com.epam.bet.interfaces.RecyclerViewClickListener
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class InboxDataAdapter(options: FirestoreRecyclerOptions<InboxMessage>, private val listener: RecyclerViewClickListener) : FirestoreRecyclerAdapter<InboxMessage, InboxDataAdapter.InboxViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InboxViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.inbox_recycler_row,
            parent, false
        )
        return InboxViewHolder(itemView, listener)
    }

    override fun onBindViewHolder(holder: InboxViewHolder, position: Int, model: InboxMessage) {
        holder.model = model
        holder.textBetShortName.text = "New bet: " + model.bet.name
        holder.textName.text = "From: " + model.from.name
        holder.textEmail.text = model.from.email
    }

    class InboxViewHolder(itemView: View, private val listener: RecyclerViewClickListener) : RecyclerView.ViewHolder(itemView) {
        var model: InboxMessage? = null
        val textBetShortName: TextView
        val textName: TextView
        val textEmail: TextView

        init {
            itemView.setOnClickListener {
                listener.onRecyclerViewItemClickListener(itemView.findViewById(R.id.inboxRow), adapterPosition)
            }
            textBetShortName = itemView.findViewById(R.id.betShortName)
            textName = itemView.findViewById(R.id.username)
            textEmail = itemView.findViewById(R.id.email)
        }

    }
}