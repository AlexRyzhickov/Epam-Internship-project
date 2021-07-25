package com.epam.bet.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.epam.bet.extensions.showToast
import com.google.firebase.firestore.FirebaseFirestore

class BetsViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val users = db.collection("users")

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