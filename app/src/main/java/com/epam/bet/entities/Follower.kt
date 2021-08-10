package com.epam.bet.entities

data class Follower(
    var name: String = "",
    var email: String = "",
){
    companion object {
        fun from(map: Map<String, String>) = object {
            val name by map.withDefault { "" }
            val email by map.withDefault { "" }

            val data = Follower(name, email)
        }.data
    }
}
