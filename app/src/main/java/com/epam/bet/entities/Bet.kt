package com.epam.bet.entities

data class Bet(var name: String = "",
               var betText: String = "",
               var ifImWin: String = "",
               var opponentEmail: String = "",
               var endDate: String = "",
               var opponentName: String = "",
               var ifOpponentWin: String = "",
               var description: String = "",)
{
    companion object {
        fun from(map: Map<String, String>) = object {
            val name by map.withDefault { "" }
            val ifImWin by map.withDefault { "" }
            val opponentEmail by map.withDefault { "" }
            val betText by map.withDefault { "" }
            val endDate by map.withDefault { "" }
            val opponentName by map.withDefault { "" }
            val ifOpponentWin by map.withDefault { "" }
            val description by map.withDefault { "" }
            val data = Bet(name, betText, ifImWin, opponentEmail, endDate, opponentName, ifOpponentWin, description)
        }.data
    }
}

