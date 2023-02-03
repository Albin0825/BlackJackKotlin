package com.example.blackjack



//==================================================
// One card in the deck
// • Value (number cards is the number that is on the card, dressed cards are 10 and ace is 11 or 1)
// • Suit (Clubs, diamonds, hearts, spades)
// • Rank (jack, queen, king or if it's none of them the string is empty)
//==================================================
class Card(val value: Int, val suit: String, val rank: String = "")



//==================================================
// Shoe is all the cards
// • NumDecks (Number of decks in the shoe, one deck is 52 cards)
//==================================================
class Shoe(val numDecks: Int) {
    //==================================================
    // One deck of card
    // You can add or remove elements from a mutable list, but you cannot do so with an array
    //==================================================
    private val deck = mutableListOf<Card>()



    //==================================================
    // Init makes it so everything inside of init needs to be done before going forwards
    //==================================================
    init {
        val suits = listOf("clubs", "diamonds", "hearts", "spades")
        val values = listOf(2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14)
        for (i in 1..numDecks) {
            for (suit in suits) {
                for (value in values) {
                    //==================================================
                    // Adding ranks to dressed cards depending on the value
                    //==================================================
                    if (value == 14) {
                        deck.add(Card(10, suit, "king"))
                    } else if (value == 13) {
                        deck.add(Card(10, suit, "queen"))
                    } else if (value == 12) {
                        deck.add(Card(10, suit, "jack"))
                    } else if (value == 11) {
                        deck.add(Card(11, suit, "ace"))
                    } else {
                        deck.add(Card(value, suit))
                    }
                }
            }
        }
        //==================================================
        // randomizing the order of the deck
        //==================================================
        deck.shuffle()
    }



    //==================================================
    // You remove one card from the shoe
    //   shoe = Shoe()
    //   newVariable = shoe.drawCard()
    // newVariable is the card you removed
    //==================================================
    fun drawCard(): Card = deck.removeAt(deck.lastIndex)
}