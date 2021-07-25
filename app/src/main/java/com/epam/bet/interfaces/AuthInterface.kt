package com.epam.bet.interfaces

import com.epam.bet.entities.User

interface AuthInterface {
    fun signIn(name: String, email: String)
    fun logOut()
    fun getUserData() : User
}