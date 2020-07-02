package com.jmonzon.duckhunt.common.ui

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.jmonzon.duckhunt.R
import com.jmonzon.duckhunt.common.Constantes
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var nick: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        changeFont()

        buttonStart.setOnClickListener {
            nick = editTextNick.text.toString()

            if (nick.isEmpty()) {
                editTextNick.error = getString(R.string.emptyNick)
                Log.i("LoginActivity: ", "Nick en blanco")
            } else {
                editTextNick.setText("")
                val intent = Intent(this, GameActivity::class.java)
                intent.putExtra(Constantes.EXTRA_NICK, nick)
                startActivity(intent)
                Log.i("LoginActivity: ", "Pasamos a pantalla de juego")
            }
        }
    }

    //Change font type
    private fun changeFont(){
        val typeface: Typeface = Typeface.createFromAsset(this.assets, "fonts/pixel.ttf")
        editTextNick.typeface = typeface
        buttonStart.typeface = typeface
    }
}