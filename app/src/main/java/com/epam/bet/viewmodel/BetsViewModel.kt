package com.epam.bet.viewmodel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.epam.bet.MainApp
import com.epam.bet.R
import com.epam.bet.entities.Bet
import com.epam.bet.entities.Follower
import com.epam.bet.entities.User
import com.epam.bet.extensions.showToast
import com.google.firebase.firestore.FirebaseFirestore



class BetsViewModel(application: Application) : AndroidViewModel(application) {
    private val context: Context = application
    private val db = FirebaseFirestore.getInstance()
    private val users = db.collection("users")
    var selectedFollowerNumber = 0
    var activeUser: MutableLiveData<User> = MutableLiveData()
    var betsList: MutableLiveData<List<Bet?>> = MutableLiveData()
    private lateinit var sharedPreferences: SharedPreferences
    var iFollowList: MutableLiveData<MutableList<Follower>> = MutableLiveData()

    fun getIFollowList() {
        var newList: MutableList<Follower> = mutableListOf()
        val email = getPreferenceMail()
        if (email != "none") {
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

    fun getFollowersNames(): Array<String> {
        var nameList: MutableList<String> = mutableListOf<String>()
        iFollowList.value?.forEach {
            nameList.add(it.name)
        }
        return nameList.toTypedArray()
    }


    fun fetchUser(){
        val user: User = User()
        var newBetList: MutableList<Bet> = mutableListOf()
        val email = getPreferenceMail()
        if(email!="none"){
            users.document(email!!).get().addOnSuccessListener { document ->
                user.name = document.data?.get("name").toString()
                user.email = document.data?.get("email").toString()
            }
            users.document(email!!).collection("bets").get().addOnSuccessListener { documents ->
                for (document in documents){
                    var bet: Bet = Bet()
                    bet.name = document.data?.get("name").toString()
                    bet.ifImWin = document.data?.get("if_win").toString()
                    bet.description = document.data?.get("description").toString()
                    bet.endDate = document.data?.get("end_date").toString()
                    val opponent = document.data?.get("opponent") as Map<String, String>
                    bet.opponentName = opponent["name"]!!
                    bet.opponentEmail = opponent["email"]!!
                    bet.ifOpponentWin = opponent["if_win"]!!
                    newBetList.add(bet)
                }
                user.activeBetList = newBetList
                activeUser.value = user
                setBetList()
            }
        }
    }

    fun setBetList() {
        var newBetList: MutableList<Bet> = mutableListOf()
        activeUser.value?.activeBetList?.forEach {
            val a = it.opponentEmail
            val b = iFollowList.value?.get(selectedFollowerNumber)?.email
            if(it.opponentEmail == iFollowList.value?.get(selectedFollowerNumber)?.email)
                newBetList.add(it)
        }
        betsList.value = newBetList
    }



    fun addUser(email: String, name: String, context: Context?) {
        val data = hashMapOf(
            "name" to name,
            "email" to email
        )
        users.document(email).set(data)
            .addOnSuccessListener {
                context?.showToast("You have successfully registered")
            }
            .addOnFailureListener {
                context?.showToast("You haven't registered yet")
            }
    }

    fun getPreferenceMail():String? {
        sharedPreferences = getApplication<Application>().applicationContext.getSharedPreferences(MainApp.applicationContext().getString(
            R.string.app_name), Context.MODE_PRIVATE)
        return sharedPreferences.getString("email", "none")
    }

}