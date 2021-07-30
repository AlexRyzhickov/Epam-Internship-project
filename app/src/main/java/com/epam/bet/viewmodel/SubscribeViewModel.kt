package com.epam.bet.viewmodel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.epam.bet.entities.Follower
import com.epam.bet.extensions.showToast
import com.google.firebase.firestore.FirebaseFirestore
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query

class SubscribeViewModel(application: Application) : AndroidViewModel(application) {

    private val db = FirebaseFirestore.getInstance()
    private val users = db.collection("users")
    private lateinit var sharedPreferences: SharedPreferences

    fun isItMyEmail(email: String): Boolean {
        sharedPreferences = getApplication<Application>().applicationContext.getSharedPreferences("BetAppSettings", Context.MODE_PRIVATE)
        val myEmail = sharedPreferences.getString("email", "none")
        return email == myEmail
    }

    fun isIFollow(email: String, context: Context?): Boolean {
        sharedPreferences = getApplication<Application>().applicationContext.getSharedPreferences("BetAppSettings", Context.MODE_PRIVATE)
        val userEmail = sharedPreferences.getString("email", "none")
        val emailList = mutableListOf<String>()
        var isSuccess = false
        users.document(userEmail!!).collection("i_follow").get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        emailList.add(document.data.get("email").toString())
                    }
                    isSuccess = true
                }

        if (isSuccess) {
            if (email in emailList) {
                context?.showToast("You're already following this user!")
                return true
            }
        } else {
            context?.showToast("Problem with connection !")
        }
        return false
    }

    fun addIFollow(email: String, successListener: (email: String) -> Unit, failListener: () -> Unit) {
        val myEmail = sharedPreferences.getString("email", "none")
        val myName = sharedPreferences.getString("name", "none")

        val TAG = "addIFollow debug"
        users.document(email).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                if (document != null && document.exists() && myEmail != "none") {
                    Log.d(TAG, "DocumentSnapshot data: " + task.result!!.data)
                    val newIFollow = mapOf(
                            "name" to document.data?.get("name"),
                            "email" to document.data?.get("email")
                    )
                    val newFollower = mapOf(
                            "name" to myName,
                            "email" to myEmail
                    )
                    users.document(myEmail!!).collection("i_follow").add(newIFollow)
                            .addOnSuccessListener {
                                successListener(email)
                            }.addOnFailureListener {
                                failListener()
                            }
                    users.document(email).collection("followers").add(newFollower)
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

    fun getSubscriptionOptions(): FirestoreRecyclerOptions<Follower> {
        sharedPreferences = getApplication<Application>().applicationContext.getSharedPreferences("BetAppSettings", Context.MODE_PRIVATE)
        val email = sharedPreferences.getString("email", "none")
        val query: Query = users.document(email!!).collection("i_follow")
        return FirestoreRecyclerOptions.Builder<Follower>()
                .setQuery(query, Follower::class.java)
                .build()
    }

    fun getFollowersOptions(): FirestoreRecyclerOptions<Follower> {
        sharedPreferences = getApplication<Application>().applicationContext.getSharedPreferences("BetAppSettings", Context.MODE_PRIVATE)
        val email = sharedPreferences.getString("email", "none")
        val query: Query = users.document(email!!).collection("followers")
        return FirestoreRecyclerOptions.Builder<Follower>()
                .setQuery(query, Follower::class.java)
                .build()
    }

}