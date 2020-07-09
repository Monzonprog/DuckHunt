package com.jmonzon.duckhunt.common.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jmonzon.duckhunt.R

class RankingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ranking)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Ranking"

        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, UserRankingFragment())
            .commit()
    }
}