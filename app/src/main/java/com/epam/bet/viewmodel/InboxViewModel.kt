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
        val inboxListCopy = inboxList.toList()
        inboxList = mutableListOf()
        val email = sharedPreferences.get("email")
        val query: Query = inbox.whereEqualTo("receiver.email", email)

        return FirestoreRecyclerOptions.Builder<InboxMessage>()
            .setQuery(query
            ) { snapshot ->
                val id = snapshot.id
                val receiver = snapshot.get("receiver") as Map<String, String>
                val sender = snapshot.get("sender") as Map<String, String>
                val bet = snapshot.get("bet") as MutableMap<String, String>
                bet["end_date"] = bet["end_date"].toString()


                val objectSender = Follower.from(sender)
                val objectReceiver = Follower.from(receiver)
                val objectBet = Bet.from(bet)
                objectBet.opponentName = objectSender.name
                objectBet.opponentEmail = objectSender.email
                objectBet.ifImWin = bet.getOrElse("if_receiver_wins", { "" })
                objectBet.ifOpponentWin = bet.getOrElse("if_sender_wins", { "" })

                val inboxMessage = InboxMessage(
                    id,
                    objectSender,
                    objectReceiver,
                    objectBet
                )
                if (inboxMessage !in inboxListCopy){
                    Log.d("NewInboxMessage", inboxMessage.toString())
                }
                inboxList.add(inboxMessage)
                inboxMessage
            }
            .build()
    }
    
    fun approveBet(inboxMessage: InboxMessage, successListener: () -> Unit, failListener: () -> Unit) {

        val curUserEmail = sharedPreferences.get("email")
        val TAG = "approveBet debug"

        var betForCurUser: Bet = inboxMessage.bet.copy()


        users.document(inboxMessage.from.email).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                if (document != null && document.exists() && curUserEmail != "none") {
                    Log.d(TAG, "DocumentSnapshot data: " + task.result!!.data)
                    val curUserBetMap = mapOf(
                        "name" to betForCurUser.name,
                        "description" to betForCurUser.description,
                        "if_win" to betForCurUser.ifImWin,
                        "end_date" to betForCurUser.endDate,
                        "opponent" to mapOf(
                            "name" to betForCurUser.opponentName,
                            "email" to betForCurUser.opponentEmail,
                            "if_win" to betForCurUser.ifOpponentWin
                        )
                    )

                    val opponentBetMap = mapOf(
                        "name" to inboxMessage.bet.name,
                        "description" to inboxMessage.bet.description,
                        "if_win" to inboxMessage.bet.ifOpponentWin,
                        "end_date" to inboxMessage.bet.endDate,
                        "opponent" to mapOf(
                            "name" to inboxMessage.whom.name,
                            "email" to inboxMessage.whom.email,
                            "if_win" to inboxMessage.bet.ifImWin
                        )
                    )

                    users.document(curUserEmail!!).collection("bets").add(curUserBetMap)
                        .addOnSuccessListener {
                            successListener()
                        }.addOnFailureListener {
                            failListener()
                        }
                    users.document(inboxMessage.from.email).collection("bets").add(opponentBetMap)
                    inbox.document(inboxMessage.id).delete()
                    //delete inbox
                } else {
                    Log.d(TAG, "No such document")
                    failListener()
                }
            } else {
                Log.d(TAG, "get failed with ", task.exception)
                failListener()
            }
        }
    }

    fun declineBet(inboxMessage: InboxMessage, successListener: () -> Unit, failListener: () -> Unit){
        inbox.document(inboxMessage.id).delete()
        .addOnSuccessListener {
            successListener()
        }.addOnFailureListener {
            failListener()
        }
    }

}