package com.example.cozydesks

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase

class searchActivity : AppCompatActivity() {


    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: androidx.appcompat.widget.SearchView
    private var mList = ArrayList<merrData>()
    private lateinit var adapter:merrAdapter
    private lateinit var db : FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_search)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        recyclerView = findViewById(R.id.recyclerView)
        searchView = findViewById(R.id.searchView)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        EventChangeListener()

        adapter = merrAdapter(mList)
        recyclerView.adapter = adapter


        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener,androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                filterList(p0)
                return true
            }

        })

        adapter.onItemClick = {
            val intent = Intent(this,AboutMerr::class.java)
            intent.putExtra("data",it)
            startActivity(intent)
        }


    }


    private fun filterList(query:String?){
        if(query!=null){
            val filteredList = ArrayList<merrData>()
            for(i in mList){
                if(i.title.contains(query)){
                    filteredList.add(i)
                }else if(i.subTitle.contains(query)){
                    filteredList.add(i)
                }else if(i.city.contains(query)){
                    filteredList.add(i)
                }
            }
            if(filteredList.isEmpty()){
                Toast.makeText(this,"No Data", Toast.LENGTH_LONG).show()

            }else{
                adapter.setFilteredList(filteredList)
            }
        }
    }

    private fun EventChangeListener(){
        db =FirebaseFirestore.getInstance()
        db.collection("merr")
            .get()
            .addOnSuccessListener { result ->
                for (dc in result){
                    var title:String = dc.getString("title").toString()
                    var subtitle:String = dc.getString("subTitle").toString()
                    var city:String = dc.getString("city").toString()
                    var address:String = dc.getString("address").toString()
                    var creatorID:String = dc.getString("creatorID").toString()
                    var linkToGroup:String = dc.getString("linkToGroup").toString()
                    var description:String = dc.getString("description").toString()
                    mList.add(merrData(
                        title = title,
                        subTitle = subtitle,
                        city = city,
                        address = address,
                        creatorID = creatorID,
                        linkToGroup = linkToGroup,
                        description = description))
                    //Toast.makeText(this, "",Toast.LENGTH_LONG).show()
                    adapter.notifyDataSetChanged()
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Неполучилось подключиться, попробуйте позже!",Toast.LENGTH_LONG).show()
            }

    }

}