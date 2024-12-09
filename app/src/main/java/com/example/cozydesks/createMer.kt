package com.example.cozydesks

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class createMer : AppCompatActivity() {

    private lateinit var merName: EditText
    private lateinit var loftName: EditText
    private lateinit var city: EditText
    private lateinit var loftaddress: EditText
    private lateinit var loftLinkGroup: EditText
    private lateinit var merDescription: EditText

    private lateinit var saveButton:Button
    private lateinit var cancelButton:Button

    var db = Firebase.firestore
    var id = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_create_mer)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var sp = getSharedPreferences("CD", Context.MODE_PRIVATE)
        id = sp.getString("ID","none").toString()
        //Toast.makeText(this,id,Toast.LENGTH_LONG).show()


        merName = findViewById(R.id.merCreate)
        loftName = findViewById(R.id.merLoftName)
        city = findViewById(R.id.merLocation)
        loftaddress = findViewById(R.id.merLoftAddres)
        loftLinkGroup = findViewById(R.id.merLoftGroupLink)
        merDescription = findViewById(R.id.merDescripton)
        saveButton = findViewById(R.id.SaveButtonToDataBase)
        cancelButton = findViewById(R.id.canselFromMerr)

        saveButton.setOnClickListener {

            val merr2 = merrData(
                merName.text.toString(),
                loftName.text.toString(),
                city.text.toString(),
                loftaddress.text.toString(),
                id.toString(),
                loftLinkGroup.text.toString(),
                merDescription.text.toString(),
            )


            db.collection("merr")
                .add(merr2)
                .addOnSuccessListener { documentReference ->
                    Toast.makeText(this,"Успешно Создано)",Toast.LENGTH_LONG).show()
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                }
                .addOnFailureListener { e->
                    Toast.makeText(this,"Ошибка, попробуйте заново",Toast.LENGTH_LONG).show()
                }


        }
        cancelButton.setOnClickListener {
            merName.setText("")
            loftName.setText("")
            city.setText("")
            loftaddress.setText("")
            loftLinkGroup.setText("")
            merDescription.setText("")
            finish()
        }

    }


}