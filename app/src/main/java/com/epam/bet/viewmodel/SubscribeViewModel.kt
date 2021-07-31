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

    fun isCurUserEmail(email: String): Boolean {
        val curUserEmail = getSharedPreferencesData("email")
        return email == curUserEmail
    }

    fun isIFollow(email: String, context: Context?): Boolean {
        val userEmail = getSharedPreferencesData("email")
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
        val curUserEmail = getSharedPreferencesData("email")
        val curUserName = getSharedPreferencesData("name")

        val TAG = "addIFollow debug"
        users.document(email).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                if (document != null && document.exists() && curUserEmail != "none") {
                    Log.d(TAG, "DocumentSnapshot data: " + task.result!!.data)
                    val newIFollow = mapOf(
                            "name" to document.data?.get("name"),
                            "email" to document.data?.get("email")
                    )
                    val newFollower = mapOf(
                            "name" to curUserName,
                            "email" to curUserEmail
                    )
                    users.document(curUserEmail!!).collection("i_follow").add(newIFollow)
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
        val email = getSharedPreferencesData("email")
        val query: Query = users.document(email!!).collection("i_follow")
        return FirestoreRecyclerOptions.Builder<Follower>()
                .setQuery(query, Follower::class.java)
                .build()
    }

    fun getFollowersOptions(): FirestoreRecyclerOptions<Follower> {
        val email = getSharedPreferencesData("email")
        val query: Query = users.document(email!!).collection("followers")
        return FirestoreRecyclerOptions.Builder<Follower>()
                .setQuery(query, Follower::class.java)
                .build()
    }

    fun getSharedPreferencesData(key: String, defValue: String = "none"): String? {
        if (!this::sharedPreferences.isInitialized){
            sharedPreferences = getApplication<Application>().applicationContext.getSharedPreferences("BetAppSettings", Context.MODE_PRIVATE)
        }
        return sharedPreferences.getString(key, defValue)
    }

}