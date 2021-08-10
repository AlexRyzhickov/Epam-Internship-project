package com.epam.bet.viewmodel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.epam.bet.entities.Bet
import com.epam.bet.entities.Follower
import com.epam.bet.entities.InboxMessage
import com.epam.bet.extensions.showToast
import com.google.firebase.firestore.FirebaseFirestore
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.firebase.ui.firestore.SnapshotParser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query

class InboxViewModel(application: Application) : AndroidViewModel(application) {

    private val db = FirebaseFirestore.getInstance()
    private val users = db.collection("users")
    private val inbox = db.collection("inbox")
    private lateinit var sharedPreferences: SharedPreferences


    fun getSubscriptionOptions(): FirestoreRecyclerOptions<InboxMessage> {
        val email = getSharedPreferencesData("email")
        val query: Query = inbox.whereEqualTo("receiver.email", email)

        return FirestoreRecyclerOptions.Builder<InboxMessage>()
            .setQuery(query
            ) { snapshot ->
                val receiver = snapshot.get("receiver") as Map<String, String>
                val sender = snapshot.get("sender") as Map<String, String>
                val bet = snapshot.get("bet") as Map<String, String>
                InboxMessage(
                    Follower.from(sender),
                    Follower.from(receiver),
                    Bet.from(bet)
                )
            }
            .build()
    }

    fun getSharedPreferencesData(key: String, defValue: String = "none"): String? {
        if (!this::sharedPreferences.isInitialized){
            sharedPreferences = getApplication<Application>().applicationContext.getSharedPreferences("BetAppSettings", Context.MODE_PRIVATE)
        }
        return sharedPreferences.getString(key, defValue)
    }

}