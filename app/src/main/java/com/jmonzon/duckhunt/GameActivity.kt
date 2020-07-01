package com.jmonzon.duckhunt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jmonzon.duckhunt.common.Constantes

class GameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        val bundle: Bundle? = intent.extras
        val nick: String? = bundle?.getString(Constantes.EXTRA_NICK, "Default")
    }
}