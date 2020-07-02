package com.jmonzon.duckhunt.common.ui

import android.content.Context
import android.graphics.Point
import android.graphics.Typeface
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.Display
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

    private fun initDisplay() {
        //Get screen size
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

    private fun moveDuck() {
        val min = 0
        //Prevent the duck from drawing off the screen
        val maxX: Int = widthScreen - imageViewDuck.width
        val maxY: Int = heightScreen - imageViewDuck.height

        //Generate two random number, one for X and another for Y
        var randomX = random.nextInt((maxX - min) + 1)
        var randomY = random.nextInt((maxY - min) + 1)

        //Use random number for move the duck to that point
        imageViewDuck.x = randomX.toFloat()
        imageViewDuck.y = randomY.toFloat()

    }

    //Method for count time from 60s to 0s
    private fun initCountDown() {
        object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val textToShow: String = (millisUntilFinished / 1000).toString() +"s"
                textViewTimer.text = textToShow
            }

            override fun onFinish() {
                textViewTimer.text = "0s"
            }
        }.start()
    }
}