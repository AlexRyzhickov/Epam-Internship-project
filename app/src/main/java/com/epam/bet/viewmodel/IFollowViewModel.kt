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

class IFollowViewModel(application: Application) : AndroidViewModel(application) {

    private val db = FirebaseFirestore.getInstance()
    private val users = db.collection("users")
    var iFollowList: MutableLiveData<MutableList<Follower>> = MutableLiveData()
    private lateinit var sharedPreferences : SharedPreferences

    fun getIFollowList(){
        sharedPreferences = getApplication<Application>().applicationContext
            .getSharedPreferences("BetAppSettings", Context.MODE_PRIVATE)
        var newList: MutableList<Follower> = mutableListOf()
        val email = sharedPreferences.getString("email", "none")
        if(email!="none") {
            users.document(email!!).collection("i_follow").get().addOnSuccessListener { documents ->
                for (document in documents) {
                    var follower: Follower = Follower(
                        document.data?.get("name").toString(),
                        document.data?.get("email").toString()
                    )
                    newList.add(follower)
                }
                iFollowList.value = newList
            }
        }
    }

    fun isIFollow(email: String): Boolean {
        for (v in iFollowList.value!!){
            if (v.email == email)
                return true;
        }
        return false;
    }

    fun addIFollow(email: String, successListener: (email: String) -> Unit, failListener: () -> Unit){

        var newList: MutableList<Follower> = mutableListOf()

        val myEmail = sharedPreferences.getString("email", "none")
        val myName = sharedPreferences.getString("name", "none")

        val TAG = "addIFollow debug"
        users.document(email).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                if (document != null && myEmail != "none") {
                    Log.d(TAG, "DocumentSnapshot data: " + task.result!!.data)
                    val newIFollow = mapOf(
                        "name" to document.data?.get("name"),
                        "email" to document.data?.get("email")
                    )
                    val newFollower = mapOf(
                        "name" to myName,
                        "email" to myEmail
                    )

                    users.document(myEmail!!).collection("i_follow").add(newIFollow).addOnSuccessListener {
                        iFollowList.value?.add(Follower(document.data?.get("name").toString(), document.data?.get("email").toString()))
                        successListener(email)
                    }. addOnFailureListener { failListener() }
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

}