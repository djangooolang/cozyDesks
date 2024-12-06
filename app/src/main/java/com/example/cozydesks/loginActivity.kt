package com.example.cozydesks

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class loginActivity : AppCompatActivity() {

    private lateinit var signInButton: TextView
    private lateinit var phoneNumber: TextView
    private lateinit var password: TextView
    private lateinit var tabRegisterButton: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }
        // Initialize
        var sp = getSharedPreferences("CD", Context.MODE_PRIVATE)
        signInButton = findViewById(R.id.loginButton)
        phoneNumber = findViewById(R.id.loginPhoneNumber)
        password = findViewById((R.id.loginPassword))
        tabRegisterButton = findViewById(R.id.regTabButton)

        var db = Firebase.firestore


        tabRegisterButton.setOnClickListener{
            startActivity(Intent(this,register_Activity::class.java))
        }


        signInButton.setOnClickListener{
            var num = phoneNumber.text.trim().toString()
            var psswd = password.text.trim().toString()

            db.collection("users")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        if(document.getString("phoneNumber") == num ){
                            if (document.getString("password") == psswd){
                                sp.edit().putString("LoginVeriFF","FullLogin").commit()
                                startActivity(Intent(this,MainActivity::class.java))
                            }

                        }
                    }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(this,"Ошибка, попробуйте позже", Toast.LENGTH_LONG).show()
                }
        }
    }
    override fun onStart() {
        super.onStart()
        var sp = getSharedPreferences("CD", Context.MODE_PRIVATE)
        if(sp.getString("LoginVeriFF","0") == "FullLogin"){
            startActivity(Intent(this,register_Activity::class.java))
        }
    }
}