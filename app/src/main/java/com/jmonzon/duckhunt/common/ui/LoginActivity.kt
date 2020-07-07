package com.jmonzon.duckhunt.common.ui

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.auth.User
import com.jmonzon.duckhunt.R
import com.jmonzon.duckhunt.common.Constantes
import com.jmonzon.duckhunt.common.models.UserModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var nick: String
    private lateinit var db: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        changeFont()
        connectToFireStore()

        buttonStart.setOnClickListener {
            nick = editTextNick.text.toString()

            if (nick.isEmpty()) {
                editTextNick.error = getString(R.string.emptyNick)
                Log.i("LoginActivity: ", "Nick en blanco")
            } else {
                addNickAndStart()
            }
        }
    }

    private fun addNickAndStart() {
        db.collection("users").whereEqualTo("nick", nick)
            .get()
            .addOnSuccessListener { queryDocumentSnapshots ->
                if(queryDocumentSnapshots.size() > 0){
                    editTextNick.error = "El nick no está disponible"
                    Log.i("Registro en la BBDD: ", "El usuario ya existe")
                } else{
                    addNickToFireStore()
                }
            }
    }

    //Connect to Firebase DB
    private fun connectToFireStore() {
        db = FirebaseFirestore.getInstance()
    }

    //Change font type
    private fun changeFont() {
        val typeface: Typeface = Typeface.createFromAsset(this.assets, "fonts/pixel.ttf")
        editTextNick.typeface = typeface
        buttonStart.typeface = typeface
    }

    private fun addNickToFireStore() {
        db.collection("users").
            add(UserModel(nick, 0))
            .addOnSuccessListener { documentReference ->
                editTextNick.setText("")
                val intent = Intent(this, GameActivity::class.java)
                intent.putExtra(Constantes.EXTRA_NICK, nick)
                intent.putExtra(Constantes.EXTRA_ID, documentReference.id)
                startActivity(intent)
                Log.i("Registro en la BBDD: ", "Registro ok")
                Log.i("LoginActivity: ", "Pasamos a pantalla de juego")
            }

    }
}