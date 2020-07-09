package com.jmonzon.duckhunt.common.ui

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jmonzon.duckhunt.R
import com.jmonzon.duckhunt.common.models.UserModel

class MyUserModelRecyclerViewAdapter(
    private val values: List<UserModel>
) : RecyclerView.Adapter<MyUserModelRecyclerViewAdapter.ViewHolder>() {

    private lateinit var typeface: Typeface

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_user_ranking, parent, false)
        typeface = Typeface.createFromAsset(parent.context.assets, "fonts/pixel.ttf")
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        var pos : String = (position + 1).toString() + "ª"
        holder.textViewPosition.text = pos
        holder.textViewNickname.text = item.nick
        holder.textViewDucks.text = item.ducks.toString()

        //Change font type

        holder.textViewPosition.typeface = typeface
        holder.textViewNickname.typeface = typeface
        holder.textViewDucks.typeface = typeface
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var textViewPosition: TextView = view.findViewById(R.id.textViewPosition)
        var textViewNickname: TextView = view.findViewById(R.id.textViewNick)
        var textViewDucks: TextView = view.findViewById(R.id.textViewDucks)
    }
}