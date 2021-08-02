package com.epam.bet.entities

data class Bet(var name: String = "",
               var ifImWin: String = "",
               var opponentEmail: String = "",
               var endDate: String = "",
               var opponentName: String = "",
               var ifOpponentWin: String = "",
               var description: String = "", )