package com.epam.bet.viewmodel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.epam.bet.entities.Bet
import com.epam.bet.entities.Follower
import com.epam.bet.entities.User
import com.epam.bet.extensions.showToast
import com.google.firebase.firestore.FirebaseFirestore



class BetsViewModel(application: Application) : AndroidViewModel(application) {

    private val db = FirebaseFirestore.getInstance()
    private val users = db.collection("users")
    var activeUser: MutableLiveData<User> = MutableLiveData()
    var betsList: MutableLiveData<List<Bet?>> = MutableLiveData()
    private lateinit var sharedPreferences : SharedPreferences


    fun fetchUser(){
        sharedPreferences = getApplication<Application>().applicationContext.getSharedPreferences("BetAppSettings", Context.MODE_PRIVATE)
        val user: User = User()
        var newBetList: MutableList<Bet> = mutableListOf()
        val email = sharedPreferences.getString("email", "none")
        if(email!="none"){
            users.document(email!!).get().addOnSuccessListener { document ->
                user.name = document.data?.get("name").toString()
                user.email = document.data?.get("email").toString()
            }
            users.document(email!!).collection("bets").get().addOnSuccessListener { documents ->
                for (document in documents){
                    var bet: Bet = Bet()
                    bet.name = document.data?.get("name").toString()
                    bet.betText = document.data?.get("description").toString()
                    newBetList.add(bet)
                }
                //user.activeBetList = document.data?.get("bets") as MutableList<Bet>
                user.activeBetList = newBetList
                //activeUser.value = user
                //betsList.value = activeUser.value?.activeBetList
                activeUser.value = user
                betsList.value = activeUser.value?.activeBetList
            }

        }
    }


    //ALERT HARDCODE SHIT
    fun addBet(){
        betsList.value = activeUser.value?.activeBetList
        /*
        var newBetList: MutableList<Bet> = mutableListOf()
        var bet: Bet = Bet("Name", "aaaaaaaa@gmail.com", "andrei@gmail.com", "Blablabla")
        newBetList.add(bet)
        betsList.value = newBetList
         */
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

}