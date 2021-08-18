package com.epam.bet.entities

data class Bet(var name: String = "",
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
            val if_win by map.withDefault { "" }
            val opponentEmail by map.withDefault { "" } //doesnt work
            val end_date by map.withDefault { "" }
            val opponentName by map.withDefault { "" } //doesnt work
            val ifOpponentWin by map.withDefault { "" } //doesnt work
            val description by map.withDefault { "" }
            val data = Bet(name, if_win, opponentEmail, end_date, opponentName, ifOpponentWin, description)
        }.data
    }
}

