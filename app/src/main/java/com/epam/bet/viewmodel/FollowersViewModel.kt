package com.epam.bet.viewmodel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.epam.bet.entities.Bet
import com.epam.bet.entities.Follower
import com.epam.bet.entities.User
import com.google.firebase.firestore.FirebaseFirestore

class FollowersViewModel(application: Application) : AndroidViewModel(application) {

    private val db = FirebaseFirestore.getInstance()
    private val users = db.collection("users")
    var followersList: MutableLiveData<List<Follower>> = MutableLiveData()
    private lateinit var sharedPreferences : SharedPreferences

    fun getFollowersList(){
        sharedPreferences = getApplication<Application>().applicationContext
            .getSharedPreferences("BetAppSettings", Context.MODE_PRIVATE)
        var newList: MutableList<Follower> = mutableListOf()
        val email = sharedPreferences.getString("email", "none")
        if(email!="none"){
            users.document(email!!).collection("followers").get().addOnSuccessListener { documents ->
                for (document in documents){
                    var follower: Follower = Follower(
                        document.data?.get("name").toString(), document.data?.get("email").toString()
                    )
                    newList.add(follower)
                }
                followersList.value = newList
            }
        }

    }
}