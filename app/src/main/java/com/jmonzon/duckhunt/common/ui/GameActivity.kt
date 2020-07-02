package com.jmonzon.duckhunt.common.ui

import android.content.Context
import android.content.DialogInterface
import android.graphics.Point
import android.graphics.Typeface
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.Display
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.jmonzon.duckhunt.R
import com.jmonzon.duckhunt.common.Constantes
import kotlinx.android.synthetic.main.activity_game.*
import java.util.*


class GameActivity : AppCompatActivity() {
    private var counter: Int = 0
    private var widthScreen = 0
    private var heightScreen = 0
    private lateinit var random: Random
    lateinit var context: Context
    private var gameOver: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        context = this
        setNick()
        changeFont()
        initDisplay()
        eventsClickDuck()
        initCountDown()
    }

    //Get nick and use it
    private fun setNick() {
        val bundle: Bundle? = intent.extras
        val nick: String? = bundle?.getString(Constantes.EXTRA_NICK, "Default")
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
                }, 250L)
            }
        }

    }

    private fun moveDuck() {
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
            }
        }.start()
    }

    //Show dialog when game is finish
    private fun showDialogGameOver() {
        val builder: AlertDialog.Builder = this.let {
            AlertDialog.Builder(it)
        }

        builder.apply {
            setPositiveButton(R.string.restart
            ) { dialog, id ->
                // User clicked OK button
            }
            setNegativeButton(
                R.string.exit
            ) { dialog, id ->
                dialog.dismiss()
            }
        }

        val dialogText: String = resources.getString(R.string.duck_hunted, counter)
        builder.setMessage(dialogText)
            .setTitle(R.string.game_over)

        val dialog: AlertDialog? = builder.create()
        dialog!!.show()
    }
}