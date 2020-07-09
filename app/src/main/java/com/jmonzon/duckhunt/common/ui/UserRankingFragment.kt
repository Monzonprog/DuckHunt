package com.jmonzon.duckhunt.common.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.jmonzon.duckhunt.R
import com.jmonzon.duckhunt.common.models.UserModel
import kotlinx.android.synthetic.main.activity_login.*

class UserRankingFragment : Fragment() {

    private var columnCount = 1
    val ARG_COLUMN_COUNT = "column-count"
    lateinit var userList: List<UserModel>
    lateinit var adapter: MyUserModelRecyclerViewAdapter
    private lateinit var db: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectToFireStore()

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user_ranking_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }

                db.collection("users")
                    .orderBy("ducks", Query.Direction.DESCENDING)
                    .limit(10)
                    .get()
                    .addOnCompleteListener {
                        userList = ArrayList()
                        for (document: DocumentSnapshot in it.result!!){
                            val user: UserModel = document.toObject(UserModel::class.java)!!
                            (userList as ArrayList<UserModel>).add(user)
                        }
                        adapter = MyUserModelRecyclerViewAdapter(userList)
                    }
            }
        }
        return view
    }

    //Connect to Firebase DB
    private fun connectToFireStore() {
        db = FirebaseFirestore.getInstance()
    }
}