package com.epam.bet.entities

data class User(
    var name: String = "",
    var email: String = "",
    var activeBetList: MutableList<Bet> = mutableListOf(),
    var followersList: MutableList<Follower> = mutableListOf(),
    var iFollowList: MutableList<Follower> = mutableListOf(),
)
