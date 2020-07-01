package com.jmonzon.duckhunt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jmonzon.duckhunt.common.Constantes
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var nick: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        buttonStart.setOnClickListener {
            nick = editTextNick.text.toString()

            if (nick.isEmpty()){
                editTextNick.error = getString(R.string.emptyNick)
            } else{
                val intent = Intent(this, GameActivity::class.java)
                intent.putExtra(Constantes.EXTRA_NICK, nick)
                startActivity(intent)
            }
        }
    }
}