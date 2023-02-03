package com.example.blackjack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    //==================================================
    // Objects on the screen. for example, buttons, text and images
    //==================================================
    lateinit var start: Button
    lateinit var take: Button
    lateinit var Double: Button
    lateinit var stand: Button

    lateinit var playerScore: TextView
    lateinit var pcScore: TextView
    lateinit var condision: TextView

    lateinit var buttonLayout: LinearLayout

    val handler = Handler()



    //==================================================
    // Import classes from other files
    //==================================================
    val shoe = Shoe(6) //Imports the class Shoe from the file Shoe

    val player = Hand() //Imports the class Hand from the file Hand
    val pc = Hand() //Imports the class Hand from the file Hand

    val check = Check() //Imports the class Check from the file shoe



    //==================================================
    // Main
    //==================================================
    override fun onCreate(savedInstanceState: Bundle?) {
        //super.onCreate(savedInstanceState) calls the onCreate method of the parent class,
        //typically the AppCompatActivity. This method is called to initialize the activity.
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main) sets the layout of the activity using the resource file activity_main which
        //is located in the res/layout directory. This layout defines the user interface of the activity.
        setContentView(R.layout.activity_main)
        buttonLayout = findViewById(R.id.buttonLayout)



        //==================================================
        // Adds names to the hands
        //==================================================
        player.name = "player"
        pc.name = "pc"



        //==================================================
        // Start
        //==================================================
        start = findViewById(R.id.start)
        start.setOnClickListener {
            //Makes the start button invisible
            start.visibility = View.INVISIBLE

            for(i in 1..4) {
                handler.postDelayed({

                    if(i % 2 == 0) { //even
                        createCard(player)
                    }
                    else { //odd
                        createCard(pc)
                    }
                }, 300 * i.toLong())
            }

            //Makes the buttonLayout visible (You can see the Take, Double, Stand button)
            buttonLayout.visibility = View.VISIBLE
        }



        //==================================================
        // Take
        //==================================================
        take = findViewById(R.id.take)
        take.setOnClickListener {
            //For more information look in the file MainActivity, function createCard
            createCard(player)
        }



        //==================================================
        // Double
        //==================================================
        Double = findViewById(R.id.Double)
        Double.setOnClickListener {
            //For more information look in the file MainActivity, function createCard
            createCard(player)

            end()
        }



        //==================================================
        // Stand
        //==================================================
        stand = findViewById(R.id.stand)
        stand.setOnClickListener {
            end()
        }
    }



    //==================================================
    // Creates a card
    // • Rank (jack, queen, king or if it's none of them the string is empty)
    // • Value (number cards is the number that is on the card, dressed cards are 10 and ace is 11 or 1)
    // • Suit (Clubs, diamonds, hearts, spades)
    // • Who (Which container the cards should end up in)
    //==================================================
    fun createCard(who: Hand) {
        ////For more information look in the file Shoe, function drawCard
        val card = shoe.drawCard()
        if(card.rank == "ace") {
            who.ace += 1
        }



        //Creates card
        val imageView = ImageView(this)

        //Adds image on card
        var ImageResource = resources.getIdentifier("${card.suit}_of_${card.value}", "drawable", packageName)
        if(card.rank != "") {
            ImageResource = resources.getIdentifier("${card.suit}_of_${card.rank}", "drawable", packageName)
        }

        //Adds styling to card
        imageView.setImageResource(ImageResource)
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            1f,
        )
        imageView.layoutParams = params

        //Placing card in LinearLayout (in this case either in playerCards or pcCards)
        val whosCardsContainer = findViewById<LinearLayout>(resources.getIdentifier("${who.name}Cards", "id", packageName))
        whosCardsContainer.addView(imageView)



        //For more information look in the file MainActivity, function printScore
        if(who.num > 10 && card.value == 11) {
            printScore(1, who)
        } else {
            printScore(card.value, who)
        }



        //For more information look in the file Check, function over21 and winCon
        val over21 = check.over21(who)
        if(over21) {
            end()
        }
    }



    //==================================================
    // Updates the score of that person
    // • Value (number cards is the number that is on the card, dressed cards are 10 and ace is 11 or 1)
    // • Who (Adds the value/score to that person. for example, player or pc)
    //==================================================
    fun printScore(value: Int, who: Hand) {
        //Adding the number to that persons total score
        who.num += value
        //Updates the score that is show off that person
        val whoScore = findViewById<TextView>(resources.getIdentifier("${who.name}Score", "id", packageName))
        whoScore.text = who.num.toString()
    }



    //==================================================
    // Updates the text of the win condition
    // • WinCon (The text that is showing up when you get a Draw/Win/Lose)
    //==================================================
    fun end() {
        if(player.num <= 21 && pc.num < 17) {
            for (i in pc.num until 17) {
                handler.postDelayed({
                    if (pc.num < 17) {
                        //For more information look in the file MainActivity, function createCard
                        createCard(pc)
                    }
                    if(i == 16) {
                        end()
                    }
                }, 150 * i.toLong())
            }
        } else {
            printCondision(check.winCon(player, pc))
        }
    }



    //==================================================
    // Updates the text of the win condition
    // • WinCon (The text that is showing up when you get a Draw/Win/Lose)
    //==================================================
    fun printCondision(winCon: String) {
        //Makes the buttonLayout invisible (You can not see the Take, Double, Stand button)
        buttonLayout.visibility = View.GONE
        //Updates the text to winCon
        condision = findViewById(R.id.condision)
        condision.text = winCon
    }
}