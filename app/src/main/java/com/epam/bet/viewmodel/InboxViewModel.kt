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

class InboxViewModel(application: Application) : AndroidViewModel(application) {

    private val db = FirebaseFirestore.getInstance()
    private val users = db.collection("users")
    private lateinit var sharedPreferences: SharedPreferences


    fun getSubscriptionOptions(): FirestoreRecyclerOptions<Follower> {
        val email = getSharedPreferencesData("email")
        val query: Query = users.document(email!!).collection("i_follow")
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