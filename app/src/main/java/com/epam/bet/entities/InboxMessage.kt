package com.epam.bet.entities

data class InboxMessage (
    var id: String,
    var from: Follower,
    var whom: Follower,
    var bet: Bet

)