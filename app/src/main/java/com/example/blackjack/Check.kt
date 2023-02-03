package com.example.blackjack

class Check() {
    //==================================================
    // Checks if the person is over/equal to 21
    // • Who (The person)
    //==================================================
    fun over21(who: Hand): Boolean {
        return who.num >= 21 //returns true or false
    }



    //==================================================
    // Checks if player have won or lost
    //==================================================
    fun winCon(player: Hand, pc: Hand): String {
        if(player.num == pc.num) {
            return "DRAW"
        }
        else if (player.num <= 21 && pc.num <= 21) {
            if (player.num > pc.num) {
                return "WIN"
            } else {
                return "LOSE"
            }
        }
        else if(player.num > 21) {
            return "LOSE"
        }
        else if(pc.num > 21) {
            return "WIN"
        }
        else {
            return "Something went wrong"
        }
    }
}