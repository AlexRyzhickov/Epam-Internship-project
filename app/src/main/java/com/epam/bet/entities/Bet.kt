package com.epam.bet.entities

data class Bet(var name: String = "",
               var firstOpponentEmail: String = "",
               var secondOpponentEmail: String = "",
               var betText: String = "", )
{
    companion object {
        fun from(map: Map<String, String>) = object {
            val name by map
            val firstOpponentEmail by map.withDefault { "" }
            val secondOpponentEmail by map.withDefault { "" }
            val betText by map.withDefault { "" }
            val data = Bet(name, firstOpponentEmail, secondOpponentEmail, betText)
        }.data
    }
}