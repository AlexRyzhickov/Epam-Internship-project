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
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class InboxDataAdapter(options: FirestoreRecyclerOptions<InboxMessage>) : FirestoreRecyclerAdapter<InboxMessage, InboxDataAdapter.InboxViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InboxViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.inbox_recycler_row,
            parent, false
        )
        return InboxViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: InboxViewHolder, position: Int, model: InboxMessage) {
        holder.model = model
        holder.textBetShortName.text = "New bet: " + model.bet.name
        holder.textName.text = "From: " + model.from.name
        holder.textEmail.text = model.from.email
    }

    class InboxViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var model: InboxMessage? = null
        val textBetShortName: TextView
        val textName: TextView
        val textEmail: TextView

        init {
            itemView.setOnClickListener {
                if (model != null){
                    Log.d("dialog", model!!.from.name)
                }
            }
            textBetShortName = itemView.findViewById(R.id.betShortName)
            textName = itemView.findViewById(R.id.username)
            textEmail = itemView.findViewById(R.id.email)
        }

    }
}