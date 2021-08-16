package com.epam.bet.viewmodel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.epam.bet.entities.Bet
import com.epam.bet.entities.Follower
import com.epam.bet.entities.InboxMessage
import com.epam.bet.extensions.showToast
import com.google.firebase.firestore.FirebaseFirestore
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.firebase.ui.firestore.SnapshotParser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import org.koin.android.ext.android.get
import org.koin.core.context.GlobalContext

class InboxViewModel(application: Application) : AndroidViewModel(application) {

    private val db = FirebaseFirestore.getInstance()
    private val users = db.collection("users")
    private val inbox = db.collection("inbox")
    private val sharedPreferences : SharedPreferencesProvider by lazy { GlobalContext.get().koin.get() }//get<SharedPreferencesProvider>()
    var inboxList: MutableList<InboxMessage> = mutableListOf()

    fun getSubscriptionOptions(): FirestoreRecyclerOptions<InboxMessage> {
        inboxList = mutableListOf()
        val email = sharedPreferences.get("email")
        val query: Query = inbox.whereEqualTo("receiver.email", email)

        return FirestoreRecyclerOptions.Builder<InboxMessage>()
            .setQuery(query
            ) { snapshot ->
                val receiver = snapshot.get("receiver") as Map<String, String>
                val sender = snapshot.get("sender") as Map<String, String>
                val bet = snapshot.get("bet") as Map<String, String>
                val inboxMessage = InboxMessage(
                    Follower.from(sender),
                    Follower.from(receiver),
                    Bet.from(bet)
                )
                inboxList.add(inboxMessage)
                inboxMessage
            }
            .build()
    }

}