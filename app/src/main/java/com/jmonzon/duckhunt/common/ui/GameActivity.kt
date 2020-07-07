package com.jmonzon.duckhunt.common.ui

import android.graphics.Point
import android.graphics.Typeface
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.Display
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.jmonzon.duckhunt.R
import com.jmonzon.duckhunt.common.Constantes
import kotlinx.android.synthetic.main.activity_game.*
import java.util.*


class GameActivity : AppCompatActivity() {
    private var widthScreen = 0
    private var counter: Int = 0
    private var heightScreen = 0
    private var id: String? = ""
    private var nick: String? = ""
    private lateinit var random: Random
    private var gameOver: Boolean = false
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        connectToFireStore()
        recoverExtras()
        setNick()
        changeFont()
        initDisplay()
        eventsClickDuck()
        initCountDown()
    }

    //Connect to Firebase DB
    private fun connectToFireStore() {
        db = FirebaseFirestore.getInstance()
    }

    //Recover data from bundle
    private fun recoverExtras() {
        val bundle: Bundle? = intent.extras
        nick = bundle?.getString(Constantes.EXTRA_NICK, "Default")
        id = bundle?.getString(Constantes.EXTRA_ID, "")
    }

    private fun setNick(){
        textViewNick.text = nick
    }

    //Set font style
    private fun changeFont() {
        val typeface: Typeface = Typeface.createFromAsset(this.assets, "fonts/pixel.ttf")
        textViewTimer.typeface = typeface
        textViewCounter.typeface = typeface
        textViewNick.typeface = typeface
    }

    //Get screen size
    private fun initDisplay() {
        val display: Display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        widthScreen = size.x
        heightScreen = size.y

        //Init object for generate random number
        random = Random()
    }

    //Manage actions when click in a duck
    private fun eventsClickDuck() {
        imageViewDuck.setOnClickListener {
            if (!gameOver) {
                //Add 1 to counter and paint it when user hit the duck
                counter++
                textViewCounter.text = counter.toString()
                imageViewDuck.setImageResource(R.drawable.duck_clicked)

                //Change image after time
                Handler().postDelayed({
                    imageViewDuck.setImageResource(R.drawable.duck)
                    moveDuck()
                }, 125L)
            }
        }

    }

    private fun moveDuck() {
        imageViewDuck.visibility = View.VISIBLE
        val min = 0
        //Prevent draw the duck out of  screen
        val maxX: Int = widthScreen - imageViewDuck.width
        val maxY: Int = heightScreen - imageViewDuck.height

        //Generate two random number, one for X and another for Y
        val randomX = random.nextInt((maxX - min) + 1)
        val randomY = random.nextInt((maxY - min) + 1)

        //Use random number for move the duck to that point
        imageViewDuck.x = randomX.toFloat()
        imageViewDuck.y = randomY.toFloat()

    }

    //Method for count time since 60s to 0s
    private fun initCountDown() {
        object : CountDownTimer(6000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val textToShow: String = (millisUntilFinished / 1000).toString() + "s"
                textViewTimer.text = textToShow
            }

            override fun onFinish() {
                imageViewDuck.visibility = View.INVISIBLE
                textViewTimer.text = "0s"
                gameOver = true
                showDialogGameOver()
                saveResultFireStore()
            }
        }.start()
    }

    private fun saveResultFireStore() {
        db.collection("users")
            .document(id.toString())
            .update("ducks", counter)
    }

    //Show dialog when game is finish
    private fun showDialogGameOver() {
        val builder: AlertDialog.Builder = this.let {
            AlertDialog.Builder(it)
        }

        builder.apply {
            setPositiveButton(
                R.string.restart
            ) { dialog, id ->
                //Restart the game
                resetGame()
            }
            setNegativeButton(
                R.string.exit
            ) { dialog, id ->
                //Close the dialog and kill activity
                dialog.dismiss()
                finish()
            }
        }

        val dialogText: String = resources.getString(R.string.duck_hunted, counter)
        builder.setMessage(dialogText)
            .setTitle(R.string.game_over)

        val dialog: AlertDialog? = builder.create()
        dialog!!.show()
    }

    //Restart the game and set counters to 0
    private fun resetGame() {
        val reset = 0
        counter = reset
        gameOver = false
        moveDuck()
        textViewCounter.text = reset.toString()
        initCountDown()
    }
}