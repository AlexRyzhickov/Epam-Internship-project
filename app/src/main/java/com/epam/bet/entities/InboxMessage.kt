package com.epam.bet.entities

data class InboxMessage (
    var from: Follower,
    var whom: Follower,
    var bet: Bet

)